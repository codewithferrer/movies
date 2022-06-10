package es.trespies.movies.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.flow.Flow

@Composable
fun <T> Flow<T>.collectAsState(): State<T?> = this.collectAsState(initial = null)