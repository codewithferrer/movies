package es.trespies.movies.ui.movie

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun MovieDetailView(viewModel: MovieDetailViewModel = hiltViewModel(), navController: NavController, movieId: String?) {
    Text(text = "Detail movie - ${movieId}")
}