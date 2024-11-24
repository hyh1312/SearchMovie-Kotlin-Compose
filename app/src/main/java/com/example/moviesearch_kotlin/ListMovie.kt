package com.example.moviesearch_kotlin

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.moviesearch_kotlin.model.Movie
import com.example.moviesearch_kotlin.network.OnlineSearchUtil
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@SuppressLint("CoroutineCreationDuringComposition", "ShowToast")
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ListMovie(query: String = "") {
    val movies: MutableList<Movie> = remember { mutableListOf() }
    var page by remember { mutableStateOf(0) }
    val listState = rememberLazyListState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                ),
                title = { Text("电影列表") },
                navigationIcon = {
                    IconButton(onClick = { /*...*/ }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            tint = Color.White,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            state = listState
        ) {
            items(items = movies) { movie ->
                MoviePosterCard(movie)
            }
        }
    }

    var isLoading by remember { mutableStateOf(true) }
    if (isLoading) CircularProgress()
    OnlineSearchUtil.stopLoading = { isLoading = false }
    val coroutineScope = rememberCoroutineScope()

    val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
    val itemCount by remember { derivedStateOf { listState.layoutInfo.totalItemsCount } }
    var lastItemCount by remember { mutableStateOf(itemCount - 1) }
    if (lastItemCount != itemCount && lastVisibleItem >= itemCount - 1) {
        lastItemCount = itemCount
        isLoading = true

        coroutineScope.launch {
            try {
                movies += OnlineSearchUtil.searchMoviesByPage(query, page + 1)
                page++;
                Log.d("tag", "Load movies")
            } catch (e: Exception) {
                Toast.makeText(context, "出现错误", Toast.LENGTH_LONG)
                Log.d("tag", "Error: $e")
                isLoading = false
            }
        }

    }

}

@Preview
@Composable
fun CircularProgress() {
    Box(Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(64.dp)
                .align(Alignment.Center),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}

@Composable
fun MoviePosterCard(movie: Movie) {
    val title = movie.Title
    val year = movie.Year
    val id = movie.imdbID
    val posterUrl = movie.Poster
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(posterUrl),
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