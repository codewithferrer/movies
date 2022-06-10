package es.trespies.movies.services

import android.content.Context
import es.trespies.movies.R
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor(private val context: Context): Interceptor {

    private val APIKEY_NAME = "api_key"

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val originalHttpUrl = request.url

        val apiKey: String = context.resources.getString(R.string.apikey)

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter(APIKEY_NAME, apiKey)
            .build()



        val newRequest = request.newBuilder()
            .url(url)
            .build()

        return chain.proceed(newRequest)
    }

}