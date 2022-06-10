package es.trespies.movies.repositories

import androidx.annotation.MainThread
import es.trespies.movies.api.ApiEmptyResponse
import es.trespies.movies.api.ApiErrorResponse
import es.trespies.movies.api.ApiResponse
import es.trespies.movies.api.ApiSuccessResponse
import es.trespies.movies.services.CoroutineAppExecutors
import es.trespies.movies.util.Resource
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

/**
 * Read data from database and API WS
 */
fun <ResultType, RequestType> networkBoundResource(
    coroutineAppExecutors: CoroutineAppExecutors,
    saveCallResult: suspend (RequestType) -> Unit,
    shouldFetch: () -> Boolean = { true },
    loadFromDb: suspend () -> Flow<ResultType>,
    fetch: suspend () -> ApiResponse<RequestType>,
    processResponse: (suspend (ApiSuccessResponse<RequestType>) -> RequestType) = { it.body },
    onFetchFailed: ((ApiErrorResponse<RequestType>) -> Unit)? = null
): Flow<Resource<ResultType>> {
    return NetworkBoundResource(
        coroutineAppExecutors = coroutineAppExecutors,
        saveCallResult = saveCallResult,
        shouldFetch = shouldFetch,
        loadFromDb = loadFromDb,
        fetch = fetch,
        processResponse = processResponse,
        onFetchFailed = onFetchFailed
    ).asFlow()
}

private class NetworkBoundResource<ResultType, RequestType>
@MainThread constructor(
    private val coroutineAppExecutors: CoroutineAppExecutors,
    private val saveCallResult: suspend (RequestType) -> Unit,
    private val shouldFetch: () -> Boolean = { true },
    private val loadFromDb: suspend () -> Flow<ResultType>,
    private val fetch: suspend () -> ApiResponse<RequestType>,
    private val processResponse: (suspend (ApiSuccessResponse<RequestType>) -> RequestType),
    private val onFetchFailed: ((ApiErrorResponse<RequestType>) -> Unit)?
) {

    private val result: Flow<Resource<ResultType>> = channelFlow {

        if (!shouldFetch()) {
            loadFromDb().map { Resource.success(it) }.collect {
                send(it)
            }
        } else {
            doFetch(this)
        }
    }

    private suspend fun doFetch(collector: ProducerScope<Resource<ResultType>>) = withContext(coroutineAppExecutors.diskIODispatcher()) {

        val firstResult = loadFromDb().firstOrNull()
        collector.send( Resource.loading(firstResult) )

        when(val response = fetchCatching()) {
            is ApiSuccessResponse, is ApiEmptyResponse -> {
                if (response is ApiSuccessResponse) {
                    val processed = processResponse(response)
                    saveCallResult(processed)
                }

                loadFromDb().collect {
                    collector.send( Resource.success(it) )
                }
            }
            is ApiErrorResponse -> {
                onFetchFailed?.invoke(response)
                loadFromDb().collect {
                    collector.send( Resource.error(response.errorMessage, it) )
                }
            }
        }
    }

    private suspend fun fetchCatching(): ApiResponse<RequestType> {
        return try {
            fetch()
        } catch (ex: CancellationException) {
            throw ex
        } catch (ex: Throwable) {
            ApiResponse.create(ex)
        }
    }

    fun asFlow(): Flow<Resource<ResultType>> = result
}