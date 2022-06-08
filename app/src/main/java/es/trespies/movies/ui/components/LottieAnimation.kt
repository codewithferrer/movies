package es.trespies.movies.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*

@Composable
fun LottieAnimation(animation: String?, width: Dp = 200.dp, height: Dp = 200.dp) {
    animation?.let {
        val composition by rememberLottieComposition(LottieCompositionSpec.Asset(it))
        val progress by animateLottieCompositionAsState(composition, iterations = LottieConstants.IterateForever)

        LottieAnimation(
            composition = composition,
            progress = progress,
            modifier = Modifier
                .width(width)
                .height(height),
            contentScale = ContentScale.Fit
        )
    }

}