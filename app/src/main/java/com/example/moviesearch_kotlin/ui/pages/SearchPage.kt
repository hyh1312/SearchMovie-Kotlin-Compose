package com.example.moviesearch_kotlin.ui.pages

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.moviesearch_kotlin.model.Movie
import com.example.moviesearch_kotlin.network.OnlineSearchUtil
import com.example.moviesearch_kotlin.ui.CircularProgress
import com.example.moviesearch_kotlin.ui.ShowMovies
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SearchPage(query: String = "", toDetail: (String) -> Unit, goBack: () -> Unit) {
    val movies: MutableList<Movie> = remember { mutableListOf() }
    var page by remember { mutableIntStateOf(0) }
    val listState = rememberLazyListState()
    val context = LocalContext.current

    ShowMovies(movies, toDetail, goBack, listState, "搜索结果")

    var isLoading by remember { mutableStateOf(true) }
    if (isLoading) CircularProgress()

    val coroutineScope = rememberCoroutineScope()
    val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
    val itemCount by remember { derivedStateOf { listState.layoutInfo.totalItemsCount } }
    var lastItemCount by remember { mutableStateOf(itemCount - 1) }

    LaunchedEffect(lastVisibleItem) {
        if (lastItemCount != itemCount && lastVisibleItem >= itemCount - 1) {
            lastItemCount = itemCount
            isLoading = true

            coroutineScope.launch {
                try {
                    movies += OnlineSearchUtil.searchMoviesByPage(
                        query,
                        page + 1
                    ) { isLoading = false }
                    page++;
                } catch (e: Exception) {
                    Toast.makeText(context, "没有更多了", Toast.LENGTH_LONG).show()
                    Log.d("tag", "Error: $e")
                    isLoading = false
                }
            }
        }
    }
}

