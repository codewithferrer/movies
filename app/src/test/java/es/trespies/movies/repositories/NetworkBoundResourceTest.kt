package es.trespies.movies.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import es.trespies.movies.services.CoroutineAppExecutors
import es.trespies.movies.util.ApiUtil
import es.trespies.movies.util.Status
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import java.util.concurrent.atomic.AtomicReference

@RunWith(JUnit4::class)
class NetworkBoundResourceTest {

    private val dbData = MutableStateFlow<Foo>(Foo(1))

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = StandardTestDispatcher()

    private val appCoroutineExecutors = Mockito.mock(CoroutineAppExecutors::class.java)

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun init() {
        //Mockito.`when`(appCoroutineExecutors.diskIODispatcher()).thenReturn(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `basic network OK`() = runTest {

        val saved = AtomicReference<Foo>()
        val results = networkBoundResource<Foo, Foo>(
            coroutineAppExecutors = appCoroutineExecutors,
            saveCallResult = {
                saved.set(it)
                dbData.value = it
            },
            loadFromDb = {
                dbData
            },
            fetch = {
                ApiUtil.successCall(Foo(2))
            }
        ).take(2).toList()

        //Verify first status
        MatcherAssert.assertThat(results.first().status, CoreMatchers.`is`(Status.LOADING))
        MatcherAssert.assertThat(results.first().data?.value, CoreMatchers.`is`(1))

        //Verify last status
        MatcherAssert.assertThat(results.last().status, CoreMatchers.`is`(Status.SUCCESS))
        MatcherAssert.assertThat(results.last().data?.value, CoreMatchers.`is`(2))

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `basic network FAIL`() = runTest {

        val saved = AtomicReference<Foo>()
        val results = networkBoundResource<Foo, Foo>(
            coroutineAppExecutors = appCoroutineExecutors,
            saveCallResult = {
                saved.set(it)
                dbData.value = it
            },
            loadFromDb = {
                dbData
            },
            fetch = {
                ApiUtil.errorCall()
            }
        ).take(2).toList()

        //Verify first status
        MatcherAssert.assertThat(results.first().status, CoreMatchers.`is`(Status.LOADING))
        MatcherAssert.assertThat(results.first().data?.value, CoreMatchers.`is`(1))

        //Verify last status
        MatcherAssert.assertThat(results.last().status, CoreMatchers.`is`(Status.ERROR))
        MatcherAssert.assertThat(results.last().data?.value, CoreMatchers.`is`(1))

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `basic database ONLY OK`() = runTest {

        val saved = AtomicReference<Foo>()
        val results = networkBoundResource<Foo, Foo>(
            coroutineAppExecutors = appCoroutineExecutors,
            saveCallResult = {
                saved.set(it)
                dbData.value = it
            },
            loadFromDb = {
                dbData
            },
            fetch = {
                ApiUtil.successCall(Foo(2))
            },
            shouldFetch = { false }
        ).take(1).toList()

        //Verify first and last status
        MatcherAssert.assertThat(results.first().status, CoreMatchers.`is`(Status.SUCCESS))
        MatcherAssert.assertThat(results.first().data?.value, CoreMatchers.`is`(1))

    }

    private data class Foo(var value: Int)

}