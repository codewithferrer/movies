package es.trespies.movies.api.interceptors

import es.trespies.movies.api.ApiResponse
import es.trespies.movies.services.Configuration
import es.trespies.movies.util.ApiResponseCallAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import okhttp3.OkHttpClient
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
import org.mockito.Mockito
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

data class ApiObjectTest(val data: String)

interface TestService {

    @GET("test_request")
    suspend fun testRequest(): ApiResponse<ApiObjectTest>

    @GET("test_request")
    suspend fun testRequestParam(@Query("param1") param1: String): ApiResponse<ApiObjectTest>

}

@RunWith(JUnit4::class)
class ApiKeyInterceptorTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var service: TestService
    private lateinit var configuration: Configuration

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun createService() {
        configuration = Mockito.mock(Configuration::class.java)


        Dispatchers.setMain(mainThreadSurrogate)

        mockWebServer = MockWebServer()

        val httpClient = OkHttpClient.Builder()

        //Add interceptor for add api_key in each call
        httpClient.addInterceptor(ApiKeyInterceptor(configuration))

        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ApiResponseCallAdapterFactory())
            .client(httpClient.build())
            .build()
            .create(TestService::class.java)

    }

    @After
    fun stopService() {
        mockWebServer.shutdown()

        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `Add query param to request OK`() = runBlocking(Dispatchers.Main) {
        enqueueResponse("test.json")
        Mockito.`when`(configuration.apiKey).thenReturn("my_api_key_value")

        val data = service.testRequest()

        val request = mockWebServer.takeRequest()

        MatcherAssert.assertThat(request.path, CoreMatchers.`is`("/test_request?api_key=my_api_key_value"))
    }

    @Test
    fun `Add query param to request with prev param OK`() = runBlocking(Dispatchers.Main) {
        enqueueResponse("test.json")
        Mockito.`when`(configuration.apiKey).thenReturn("my_api_key_value")

        val data = service.testRequestParam("param1_value")

        val request = mockWebServer.takeRequest()

        MatcherAssert.assertThat(request.path, CoreMatchers.`is`("/test_request?param1=param1_value&api_key=my_api_key_value"))
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