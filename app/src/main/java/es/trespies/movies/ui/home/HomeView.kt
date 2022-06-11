package es.trespies.movies.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import es.trespies.movies.model.Movie
import es.trespies.movies.ui.components.TitleView
import es.trespies.movies.ui.theme.ColorBlack
import es.trespies.movies.ui.theme.ColorWhite
import es.trespies.movies.ui.theme.MoviesTheme
import es.trespies.movies.ui.theme.fonts
import es.trespies.movies.util.Resource

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(viewModel: HomeViewModel = hiltViewModel(), navController: NavController) {
    Scaffold(modifier = Modifier.background(ColorWhite)) {
        val movies by viewModel.movies.collectAsState(Resource.none(null))

        HomeBodyContentView(movies = movies)

        LaunchedEffect(Unit) {
            viewModel.loadData()
        }
    }
}

@Composable
fun HomeBodyContentView(movies: Resource<List<Movie>>) {
    Column(modifier = Modifier
        .background(ColorWhite)
        .fillMaxWidth()
    ) {
        TitleView(title = "Movies · Status: ${movies.status.name}", modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp))

        val scrollState = rememberLazyGridState()

        LazyVerticalGrid(state = scrollState,
            columns = GridCells.Adaptive(minSize = 128.dp)) {
            movies.data?.let {
                items(it) { item ->
                    MovieCellView(movie = item)
                }
            }
        }
    }


}

@Composable
fun MovieView(movie: Movie) {

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

@Preview( widthDp = 400, heightDp = 800)
@Composable
fun HomeBodyContentViewPreview() {
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

        val list = listOf(
            movie, movie, movie, movie, movie, movie, movie, movie, movie, movie, movie,
        )

        HomeBodyContentView(Resource.success(list))
    }
}