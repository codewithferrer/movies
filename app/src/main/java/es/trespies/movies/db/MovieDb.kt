package es.trespies.movies.db

import androidx.room.Database
import androidx.room.RoomDatabase
import es.trespies.movies.model.Movie

@Database(
    entities = [
        Movie::class
    ],
    version = 1
)
abstract class MovieDb : RoomDatabase() {

    abstract fun movieDao(): MovieDao

}