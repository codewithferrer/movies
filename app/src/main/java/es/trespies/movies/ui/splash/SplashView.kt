package es.trespies.movies.ui.splash

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import es.trespies.movies.ui.components.LottieAnimation
import es.trespies.movies.ui.navigation.navigateToHome
import es.trespies.movies.ui.theme.ColorBlack
import es.trespies.movies.ui.theme.ColorWhite
import es.trespies.movies.ui.theme.MoviesTheme
import es.trespies.movies.ui.theme.fonts

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SplashView(viewModel: SplashViewModel = hiltViewModel(), navController: NavController) {
    Scaffold(modifier = Modifier
        .background(ColorBlack)
        .fillMaxSize()) {

        val appIsReady by viewModel.appIsReady.collectAsState()

        SplashContentView()

        LaunchedEffect(key1 = appIsReady) {
            if (appIsReady) {
                navController.navigateToHome()
            }
        }
    }


}

@Composable
fun SplashContentView() {
    Column(modifier = Modifier
        .background(ColorBlack)
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(180.dp))
        Text(
            text = "Movies",
            modifier = Modifier.padding(start = 0.dp, top = 4.dp),
            color = ColorWhite,
            fontSize = 40.sp,
            fontFamily = fonts,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(16.dp))
        LottieAnimation(animation = "splash.json", width = 300.dp, height = 300.dp)


    }
}

@Preview( widthDp = 400, heightDp = 800)
@Composable
fun SplashViewPreview() {

    MoviesTheme() {
        SplashContentView()
    }
}