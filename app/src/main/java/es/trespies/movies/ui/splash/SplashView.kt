package es.trespies.movies.ui.splash

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun SplashView(viewModel: SplashViewModel = hiltViewModel(), navController: NavController) {
    Text(text = "SplashView")


}