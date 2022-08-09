package es.trespies.movies.db

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before

abstract class MovieDbTest {

    private val mainThreadSurrogate = newSingleThreadContext("Main Thread")

    private lateinit var _db: MovieDb
    val db: MovieDb
        get() = _db

    @Before
    fun initDb() {
        Dispatchers.setMain(mainThreadSurrogate)
        _db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MovieDb::class.java
        ).build()

    }

    @After
    fun closeDb() {
        _db.close()
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }
}