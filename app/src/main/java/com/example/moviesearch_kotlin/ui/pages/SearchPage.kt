package com.example.moviesearch_kotlin.ui.pages

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.moviesearch_kotlin.model.Movie
import com.example.moviesearch_kotlin.network.OnlineSearchUtil
import com.example.moviesearch_kotlin.ui.AppBar
import com.example.moviesearch_kotlin.ui.CircularProgress
import com.example.moviesearch_kotlin.ui.ShowMovies
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SearchPage(query: String = "", toDetail: (String) -> Unit, goBack: () -> Unit) {
    val movies: MutableList<Movie> = remember { mutableStateListOf() }
    var page by remember { mutableIntStateOf(0) }
    var isLoading by remember { mutableStateOf(true) }
    val context = LocalContext.current


    val listState = rememberLazyListState()
    val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
    val itemCount by remember { derivedStateOf { listState.layoutInfo.totalItemsCount } }
    var lastItemCount by remember { mutableIntStateOf(itemCount - 1) }

    LaunchedEffect(lastVisibleItem) {
        if (lastItemCount != itemCount && lastVisibleItem >= itemCount - 1) {
            lastItemCount = itemCount
            isLoading = true
            try {
                movies += OnlineSearchUtil.searchMoviesByPage(
                    query,
                    page + 1
                ) { isLoading = false }
                page++
            } catch (e: Exception) {
                Toast.makeText(context, "没有更多了", Toast.LENGTH_LONG).show()
                Log.d("tag", "Error: $e")
                isLoading = false
            }
        }
    }

    AppBar("搜索结果", goBack) { innerPadding ->
        ShowMovies(movies, toDetail, Modifier.padding(innerPadding), listState)
        if (isLoading) CircularProgress()
    }
}

