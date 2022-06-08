package es.trespies.movies.ui.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun HomeView(viewModel: HomeViewModel = hiltViewModel(), navController: NavController) {
    Text(text = "Home")
}