package es.trespies.movies.ui.movie

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import es.trespies.movies.model.Movie
import es.trespies.movies.ui.components.BackgroundImageView
import es.trespies.movies.ui.components.TitleView
import es.trespies.movies.ui.theme.ColorWhite
import es.trespies.movies.ui.theme.MoviesTheme
import es.trespies.movies.util.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailView(viewModel: MovieDetailViewModel = hiltViewModel(), navController: NavController, movieId: String?) {
    Scaffold(modifier = Modifier.background(ColorWhite)) {
        val movie by viewModel.movie.collectAsState(Resource.none(null))

        MovieDetailContentView(movie = movie)

        LaunchedEffect(Unit) {
            movieId?.let {
                viewModel.setMovieId(movieId = it)
            }
        }
    }
}

@Composable
fun MovieDetailContentView(movie: Resource<Movie>) {
    Column(modifier = Modifier
        .background(ColorWhite)
        .fillMaxWidth()
    ) {
        TitleView(title = "Movie · Status: ${movie.status.name}", modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp))

        TitleView(title = "Movie · Title: ${movie.data?.title}", modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp))


        BackgroundImageView(imageUrl = movie.data?.posterPath, contentDescription = movie.data?.title)
    }


}

@Preview( widthDp = 400, heightDp = 800)
@Composable
fun MovieContentViewPreview() {
    MoviesTheme() {
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


        MovieDetailContentView(Resource.success(movie))
    }
}