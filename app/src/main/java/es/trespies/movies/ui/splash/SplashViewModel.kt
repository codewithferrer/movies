package es.trespies.movies.ui.splash

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.trespies.movies.services.CoroutineAppExecutors
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
open class SplashViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val coroutineAppExecutors: CoroutineAppExecutors
): ViewModel() {

    val appIsReady: MutableStateFlow<Boolean> = MutableStateFlow(false)

    init {
        viewModelScope.launch { checkAppIsReady() }
    }

    private suspend fun checkAppIsReady() = withContext(coroutineAppExecutors.diskIODispatcher()) {
        Thread.sleep(3500)
        appIsReady.value = true
    }

}