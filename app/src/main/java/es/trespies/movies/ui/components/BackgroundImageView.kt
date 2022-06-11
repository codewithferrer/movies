package es.trespies.movies.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.size.Scale
import es.trespies.movies.R

@Composable
fun BackgroundImageView(imageUrl: String?,
                        contentDescription: String?,
                        @DrawableRes placeholderDrawableRes: Int = R.drawable.ic_launcher_background
) {
    val painter = if (imageUrl == null)
        painterResource(id = placeholderDrawableRes)
    else
        rememberImagePainter(
            data = imageUrl,
            onExecute = ImagePainter.ExecuteCallback { _, _ -> true },
            builder = {
                crossfade(true)
                placeholder(placeholderDrawableRes)

                error(placeholderDrawableRes)
                fallback(placeholderDrawableRes)
                scale(Scale.FILL)
            }
        )

    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
}