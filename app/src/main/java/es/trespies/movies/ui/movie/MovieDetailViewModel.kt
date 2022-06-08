package es.trespies.movies.ui.movie

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class MovieDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
): ViewModel() {
}