package es.trespies.movies.repositories.paging

import androidx.annotation.MainThread
import es.trespies.movies.api.ApiEmptyResponse
import es.trespies.movies.api.ApiErrorResponse
import es.trespies.movies.api.ApiResponse
import es.trespies.movies.api.ApiSuccessResponse
import es.trespies.movies.services.CoroutineAppExecutors
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

class NextPageHandler<RequestType>
@MainThread constructor(
    private val coroutineAppExecutors: CoroutineAppExecutors,
    private val saveCallResult: suspend (RequestType) -> Unit,
    private val fetch: suspend (Int) -> ApiResponse<RequestType>,
    private val processResponse: (suspend (ApiSuccessResponse<RequestType>) -> RequestType) = { it.body },
    private val processHasMorePages: (suspend (ApiSuccessResponse<RequestType>) -> Boolean)
) {

    private var numPage: Int = 0
    private var hasMore: Boolean = true
    private var isRunning: Boolean = false

    private var producerScope: ProducerScope<LoadMoreState>? = null

    private val result: Flow<LoadMoreState> = channelFlow {
        producerScope = this
        awaitClose {  }
    }

    private suspend fun doFetch() = withContext(coroutineAppExecutors.diskIODispatcher()) {

        when(val response = fetchCatching()) {
            is ApiSuccessResponse -> {
                val processed = processResponse(response)
                saveCallResult(processed)

                val hasMorePages = processHasMorePages(response)

                isRunning = false
                hasMore = hasMorePages

                producerScope?.send(LoadMoreState(running = isRunning, hasMorePages = hasMore, errorMessage = null))
            }
            is ApiEmptyResponse -> {
                isRunning = false
                hasMore = false
                producerScope?.send(LoadMoreState(running = false, hasMorePages = false, errorMessage = null))
            }
            is ApiErrorResponse -> {
                isRunning = false
                hasMore = false
                producerScope?.send(LoadMoreState(running = false, hasMorePages = false, errorMessage = response.errorMessage))
            }
        }
    }

    private suspend fun fetchCatching(): ApiResponse<RequestType> {
        return try {
            fetch(numPage)
        } catch (ex: CancellationException) {
            throw ex
        } catch (ex: Throwable) {
            ApiResponse.create(ex)
        }
    }

    suspend fun queryNextPage() = withContext(coroutineAppExecutors.diskIODispatcher()) {
        if (isRunning) return@withContext
        if (!hasMore) return@withContext

        numPage++

        isRunning = true
        producerScope?.send(LoadMoreState(running = true, errorMessage = null))

        doFetch()
    }

    fun reset() {
        numPage = 0
        hasMore = true
        isRunning = false
    }

    fun asFlow(): Flow<LoadMoreState> = result
}