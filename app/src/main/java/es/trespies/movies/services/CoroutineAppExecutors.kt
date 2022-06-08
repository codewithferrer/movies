package es.trespies.movies.services

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class CoroutineAppExecutors @Inject constructor() {

    fun diskIODispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    fun networkDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    fun defaultDispatcher(): CoroutineDispatcher {
        return Dispatchers.Default
    }

    fun mainDispatcher(): CoroutineDispatcher {
        return Dispatchers.Main
    }
}