package es.trespies.movies.ui.movie

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import es.trespies.movies.model.Movie
import es.trespies.movies.repositories.MoviesRepository
import es.trespies.movies.util.ObjectId
import es.trespies.movies.util.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@HiltViewModel
open class MovieDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val moviesRepository: MoviesRepository
): ViewModel() {

    private var _movieId: ObjectId<String> = ObjectId(null)
    private var refreshState: MutableStateFlow<ObjectId<String>> = MutableStateFlow(ObjectId(null))

    @OptIn(ExperimentalCoroutinesApi::class)
    val movie: Flow<Resource<Movie>> = refreshState.flatMapLatest { it.obj?.let { movieId -> moviesRepository.movie(movieId = movieId) } ?: flowOf(Resource.none(null)) }

    fun setMovieId(movieId: String) {
        _movieId = ObjectId(movieId)
        refreshState.value = _movieId
    }

    fun refresh() {
        refreshState.value = _movieId.apply { this.upgrade() }
    }

}