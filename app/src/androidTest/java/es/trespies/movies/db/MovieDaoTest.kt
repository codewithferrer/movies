package es.trespies.movies.db

import androidx.test.ext.junit.runners.AndroidJUnit4
import es.trespies.movies.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieDaoTest : MovieDbTest() {

    @Test
    fun when_insert_load_same_data() = runBlocking(Dispatchers.Main) {
        val list = listOf(
            Movie(id = "01", imdbId = "imdb01", title = "title_01", originalTitle = "original_title_01",
                originalLang = "en", overview = "overview_01", voteAverage = 4.3f, voteCount = 1000L,
                posterPath = null, releaseDate = "release_date_01", popularity = 5f,
                releaseDateTimestamp = 12132313132L),
            Movie(id = "02", imdbId = "imdb02", title = "title_02", originalTitle = "original_title_02",
                originalLang = "en", overview = "overview_02", voteAverage = 2.8f, voteCount = 900L,
                posterPath = null, releaseDate = "release_date_02", popularity = 4.5f,
                releaseDateTimestamp = 12132313132L),
        )

        //Método a testear
        db.movieDao().insert(list)

        val loaded = db.movieDao().loadMovies().take(1).toList()[0]

        //Verificaciones
        assertThat(loaded.size, CoreMatchers.`is`(2))
    }

    @Test
    fun when_insert_repeated_replace_data() = runBlocking(Dispatchers.Main) {

        val list = listOf(
            Movie(id = "02", imdbId = "imdb02", title = "title_02", originalTitle = "original_title_02",
                originalLang = "en", overview = "overview_02", voteAverage = 2.8f, voteCount = 900L,
                posterPath = null, releaseDate = "release_date_02", popularity = 4.5f,
                releaseDateTimestamp = 12132313132L),
        )

        // Método a testear
        db.movieDao().insert(list)

        val loaded01 = db.movieDao().loadMovies().take(1).toList()[0]
        assertThat(loaded01.size, CoreMatchers.`is`(1))

        assertThat(loaded01[0].imdbId, CoreMatchers.`is`("imdb02"))

        val list2 = listOf(Movie(id = "02", imdbId = "imdb02_new", title = "title_02b", originalTitle = "original_title_02b",
            originalLang = "en", overview = "overview_02b", voteAverage = 2.8f, voteCount = 900L,
            posterPath = null, releaseDate = "release_date_02b", popularity = 4.5f,
            releaseDateTimestamp = 12132313132L),)

        db.movieDao().insert(list2)

        val loaded02 = db.movieDao().loadMovies().take(1).toList()[0]
        assertThat(loaded02.size, CoreMatchers.`is`(1))

        assertThat(loaded02[0].imdbId, CoreMatchers.`is`("imdb02_new"))

    }

    @Test
    fun when_load_on_movie_get_correct_entry() = runBlocking(Dispatchers.Main) {
        val list = listOf(
            Movie(id = "01", imdbId = "imdb01", title = "title_01", originalTitle = "original_title_01",
                originalLang = "en", overview = "overview_01", voteAverage = 4.3f, voteCount = 1000L,
                posterPath = null, releaseDate = "release_date_01", popularity = 5f,
                releaseDateTimestamp = 12132313132L),
            Movie(id = "02", imdbId = "imdb02", title = "title_02", originalTitle = "original_title_02",
                originalLang = "en", overview = "overview_02", voteAverage = 2.8f, voteCount = 900L,
                posterPath = null, releaseDate = "release_date_02", popularity = 4.5f,
                releaseDateTimestamp = 12132313132L),
        )


        db.movieDao().insert(list)

        val loaded = db.movieDao().loadMovie("02").take(1).toList()[0]

        assertThat(loaded.id, CoreMatchers.`is`("02"))
        assertThat(loaded.imdbId, CoreMatchers.`is`("imdb02"))
    }
}