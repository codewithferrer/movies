package es.trespies.movies.services

import android.content.Context
import es.trespies.movies.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Configuration @Inject constructor(val context: Context) {

    val urlImages: String = context.resources.getString(R.string.url_images)

    val apiKey: String = context.resources.getString(R.string.apikey)

}