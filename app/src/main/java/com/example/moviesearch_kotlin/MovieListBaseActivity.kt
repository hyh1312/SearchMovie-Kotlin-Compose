package com.example.moviesearch_kotlin

import android.os.Bundle
import android.widget.ProgressBar
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviesearch_kotlin.model.Movie
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter



open class MovieListBaseActivity : ComponentActivity() {
    var progressBar: ProgressBar? = null
    var movies: List<Movie> = listOf(Movie("title","year","","id"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isLoading(true)
        setContent {
            ListMovie()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Preview
    @Composable
    fun ListMovie(){
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = Color.White
                    ),
                    title = { Text("电影列表") }
                )
            }
        ) { innerPadding ->
            isLoading(true)
            LazyColumn (modifier = Modifier.padding(innerPadding)){
                items(items = movies){ movie->
                    MoviePosterCard(movie)
                }
            }
        }
    }

    @Composable
    fun MoviePosterCard(movie: Movie) {
        val title = movie.title
        val year = movie.year
        val id = movie.id
        val posterUrl = movie.posterUrl
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter("https://i1.hdslb.com/bfs/archive/a048a348163675114c696168d7bf4308c49f4696.jpg@672w_378h_1c_!web-home-common-cover.avif"),
                contentDescription = null,
                modifier = Modifier
                    .width(160.dp)
                    .height(120.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,

            )

            Spacer(Modifier.padding(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "首映时间：",
                    fontSize = 16.sp,
                )
                Text(
                    text = year,
                    fontSize = 16.sp
                )
            }
        }
    }


    fun isLoading(p: Boolean) {
        // runOnUiThread { progressBar.visibility = if (p) View.VISIBLE else View.GONE }
    }

}


