package es.trespies.movies.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.trespies.movies.model.Movie
import es.trespies.movies.ui.components.BackgroundImageView
import es.trespies.movies.ui.theme.ColorBlack
import es.trespies.movies.ui.theme.ColorWhite
import es.trespies.movies.ui.theme.MoviesTheme
import es.trespies.movies.ui.theme.fonts

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieCellView(movie: Movie, onMovieClick: (Movie) -> Unit = {}) {

    val modifier =
        Modifier
            .padding(horizontal = 4.dp, vertical = 4.dp)
            .fillMaxWidth()


    Card(
        modifier = modifier
            .aspectRatio(0.8f)
            .clickable { onMovieClick(movie) }
        ,
        shape = RoundedCornerShape(8.dp),
        // backgroundColor = ColorWhite,
        // elevation = 4.dp

    ) {
        Box(modifier = Modifier.background(ColorWhite)) {
            BackgroundImageView(imageUrl = movie.posterPath, contentDescription = movie.title)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.67f)
                    .align(Alignment.BottomCenter)
                //.background(ColorBlack50)
                ,
                contentAlignment = Alignment.Center
            ) {
                Text(text = "${movie.title} · ${movie.originalTitle}",
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    color = ColorBlack,
                    fontSize = 16.sp,
                    fontFamily = fonts,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )


            }

        }
    }

}

@Preview( widthDp = 400, heightDp = 800)
@Composable
fun MovieCellViewPreview() {
    val movie = Movie(
        id = "fkjsh",
        imdbId = "jsfdlkjf",
        title = "Fantastic Beasts: The Secrets of Dumbledore",
        originalTitle = "Fantastic Beasts: The Secrets of Dumbledore",
        originalLang = "en",
        overview = "Professor Albus Dumbledore knows the powerful, dark wizard Gellert Grindelwald is moving to seize control of the wizarding world. Unable to stop him alone, he entrusts magizoologist Newt Scamander to lead an intrepid team of wizards and witches. They soon encounter an array of old and new beasts as they clash with Grindelwald's growing legion of followers.",
        voteAverage = 4.2f,
        voteCount = 1548,
        posterPath = "https://image.tmdb.org/t/p/original/jrgifaYeUtTnaH7NF5Drkgjg2MB.jpg",
        releaseDate = "2022-04-06",
        popularity = 2121.5f,
        releaseDateTimestamp = 4654654644
    )

    MoviesTheme {
        MovieCellView(movie = movie)
    }
}