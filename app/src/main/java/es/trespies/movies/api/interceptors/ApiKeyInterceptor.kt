package es.trespies.movies.api.interceptors

import es.trespies.movies.services.Configuration
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor(private val configuration: Configuration): Interceptor {

    private val APIKEY_NAME = "api_key"

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val originalHttpUrl = request.url

        val apiKey: String = configuration.apiKey

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter(APIKEY_NAME, apiKey)
            .build()



        val newRequest = request.newBuilder()
            .url(url)
            .build()

        return chain.proceed(newRequest)
    }

}