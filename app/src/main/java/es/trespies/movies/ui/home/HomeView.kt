package es.trespies.movies.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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

        val scrollState = rememberLazyListState()

        LazyColumn(state = scrollState, modifier = Modifier.fillMaxWidth()) {
            movies.data?.let {
                items(it) { item ->
                    MovieView(movie = item)
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
        val list = listOf<Movie>(
            //CryptoAsset("BTC", "BTC", "BitCoin"),
            //CryptoAsset("ETH", "ETH", "Ethereum")
        )

        HomeBodyContentView(Resource.success(list))
    }
}