package es.trespies.movies.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import es.trespies.movies.ui.home.HomeView
import es.trespies.movies.ui.movie.MovieDetailView
import es.trespies.movies.ui.splash.SplashView

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.SplashScreen.route) {
        composable(AppScreens.SplashScreen.route) {
            SplashView(navController = navController)
        }
        composable(AppScreens.HomeScreen.route) {
            HomeView(navController = navController)
        }
        composable(AppScreens.MovieDetail.route + "/{movieId}",
        arguments = listOf(navArgument(name = "movieId") {
            type = NavType.StringType
        })) {
          MovieDetailView(navController = navController, movieId = it.arguments?.getString("movieId"))  
        }
    }
}