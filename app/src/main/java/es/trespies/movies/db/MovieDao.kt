package es.trespies.movies.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import es.trespies.movies.model.Movie
import kotlinx.coroutines.flow.Flow

@Dao
abstract class MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(movies: List<Movie>)

    @Query("SELECT * FROM Movie")
    abstract fun loadMovies(): Flow<List<Movie>>

}