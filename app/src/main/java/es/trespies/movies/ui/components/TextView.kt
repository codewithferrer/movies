package es.trespies.movies.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import es.trespies.movies.ui.theme.ColorBlack
import es.trespies.movies.ui.theme.fonts

@Composable
fun TitleView(title: String, modifier: Modifier = Modifier) {
    Text(text = title,
        modifier = modifier,
        color = ColorBlack,
        fontSize = 24.sp,
        fontFamily = fonts,
        fontWeight = FontWeight.Bold,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1
    )
}