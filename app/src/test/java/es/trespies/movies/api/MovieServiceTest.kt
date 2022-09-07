package es.trespies.movies.api

import es.trespies.movies.api.objects.ApiObjectMovie
import es.trespies.movies.util.ApiResponseCallAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class MovieServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var service: MovieService

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun createService() {
        Dispatchers.setMain(mainThreadSurrogate)

        mockWebServer = MockWebServer()

        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ApiResponseCallAdapterFactory())
            .build()
            .create(MovieService::class.java)

    }

    @After
    fun stopService() {
        mockWebServer.shutdown()

        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `Load Popular Movies OK`() = runBlocking(Dispatchers.Main) {
        enqueueResponse("popular_movies.json")

        val data = service.popularMovies()

        val request = mockWebServer.takeRequest()

        val result = (data as ApiSuccessResponse).body

        MatcherAssert.assertThat(request.path, CoreMatchers.`is`("/3/movie/popular?page=1"))
        MatcherAssert.assertThat(request.method, CoreMatchers.`is`("GET"))

        MatcherAssert.assertThat(result.results!!.size, CoreMatchers.`is`(20))

        val firstMovie: ApiObjectMovie = result.results!![0]
        MatcherAssert.assertThat(firstMovie.id, CoreMatchers.`is`("616037"))
        MatcherAssert.assertThat(firstMovie.imdbId, CoreMatchers.`is`("imdb_test"))
        MatcherAssert.assertThat(firstMovie.originalTitle, CoreMatchers.`is`("Thor: Love and Thunder"))

    }

    @Test
    fun `Load Movie OK`() = runBlocking(Dispatchers.Main) {
        enqueueResponse("movie.json")

        val data = service.movie("618529")

        val request = mockWebServer.takeRequest()

        val result = (data as ApiSuccessResponse).body

        MatcherAssert.assertThat(request.path, CoreMatchers.`is`("/3/movie/618529"))
        MatcherAssert.assertThat(request.method, CoreMatchers.`is`("GET"))

        val movie = result

        MatcherAssert.assertThat(movie.id, CoreMatchers.`is`("453395"))
        MatcherAssert.assertThat(movie.imdbId, CoreMatchers.`is`("tt9419884"))
        MatcherAssert.assertThat(movie.originalTitle, CoreMatchers.`is`("Doctor Strange in the Multiverse of Madness"))
    }

    private fun enqueueResponse(fileName: String) {
        val inputStream = javaClass.classLoader
            ?.getResourceAsStream("api-response/$fileName")?.source()?.buffer()

        mockWebServer.enqueue(
            MockResponse()
                .setBody(inputStream!!.readString(Charsets.UTF_8))
        )
    }

}