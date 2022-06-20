package es.trespies.movies.ui.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.trespies.movies.model.Movie
import es.trespies.movies.repositories.MoviesRepository
import es.trespies.movies.repositories.paging.LoadMoreState
import es.trespies.movies.util.ObjectId
import es.trespies.movies.util.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class HomeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val moviesRepository: MoviesRepository
): ViewModel() {

    private var refreshData: ObjectId<Unit> = ObjectId(null)
    private var refreshState: MutableStateFlow<ObjectId<Unit>> = MutableStateFlow(ObjectId(null))

    val loadMoreState: Flow<LoadMoreState> = moviesRepository.loadMoreState

    @OptIn(ExperimentalCoroutinesApi::class)
    val movies: Flow<Resource<List<Movie>>> = refreshState.flatMapLatest { moviesRepository.movies }

    fun loadData() {
        refresh()
    }

    fun refresh() {
        moviesRepository.resetNextPage()
        refreshState.value = refreshData.apply { this.upgrade() }
    }

    fun loadNextPage() {
        viewModelScope.launch {
            moviesRepository.queryNextPage()
        }
    }

}