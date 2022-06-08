package es.trespies.movies.ui.navigation

import androidx.navigation.NavController

sealed class AppScreens(val route: String) {
    object SplashScreen: AppScreens("splash_screen")
    object HomeScreen: AppScreens("home_screen")
    object MovieDetail: AppScreens("movie_detail")
}

inline fun NavController.navigateToHome() {
    navigate(route = AppScreens.HomeScreen.route)
}

inline fun NavController.navigateToMovieDetail(movieId: String?) {
    navigate(route = AppScreens.MovieDetail.route + "/${movieId}")
}