package es.trespies.movies.repositories

import es.trespies.movies.api.MovieService
import es.trespies.movies.db.MovieDao
import es.trespies.movies.model.Movie
import es.trespies.movies.services.Configuration
import es.trespies.movies.services.CoroutineAppExecutors
import es.trespies.movies.util.DateFormarEnum
import es.trespies.movies.util.DateUtils
import es.trespies.movies.util.Resource
import kotlinx.coroutines.flow.Flow

import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val coroutineAppExecutors: CoroutineAppExecutors,
    private val configuration: Configuration,
    private val movieService: MovieService,
    private val movieDao: MovieDao
) {

    val movies: Flow<Resource<List<Movie>>> = networkBoundResource(
        coroutineAppExecutors = coroutineAppExecutors,
        saveCallResult = { apiObject ->
                         apiObject.results?.mapNotNull {
                             it.id?.let { id -> Movie(id = id,
                                 imdbId = it.imdbId,
                                 title = it.title ?: "",
                                 originalTitle = it.originalTitle ?: "",
                                 originalLang = it.originalLang ?: "",
                                 overview = it.overview ?: "",
                                 voteAverage = it.voteAverage,
                                 voteCount = it.voteCount,
                                 //Create full path for images
                                 posterPath = it.posterPath?.let { "${configuration.urlImages}it" },
                                 releaseDate = it.releaseDate,
                                 popularity = it.popularity,
                                 releaseDateTimestamp = DateUtils.stringToLong(it.releaseDate, DateFormarEnum.YYYYMMDD)
                             ) }
                         }?.let {
                             movieDao.insert(movies = it)
                         }
        },
        loadFromDb = { movieDao.loadMovies() },
        fetch = { movieService.popularMovies() }
    )


}