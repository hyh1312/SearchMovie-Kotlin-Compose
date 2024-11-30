package com.example.moviesearch_kotlin.ui.pages

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.moviesearch_kotlin.model.Movie
import com.example.moviesearch_kotlin.model.dao
import com.example.moviesearch_kotlin.ui.AppBar
import com.example.moviesearch_kotlin.ui.CircularProgress
import com.example.moviesearch_kotlin.ui.ShowMovies

@Composable
fun HistoryPage(toDetail: (String) -> Unit, goBack: () -> Unit) {
    var isLoading by remember { mutableStateOf(true) }
    var movieList: MutableList<Movie> = remember { mutableListOf() }

    AppBar("浏览记录", goBack) { innerPadding ->
        ShowMovies(movieList, toDetail, Modifier.padding(innerPadding))
        if (isLoading) CircularProgress()
    }

    LaunchedEffect(true) {
        dao.getAll().collect { movies ->
            movieList = movies.reversed().toMutableList()
            isLoading = false
        }
    }
}

