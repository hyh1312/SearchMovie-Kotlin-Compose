package com.example.moviesearch_kotlin

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.moviesearch_kotlin.model.Movie
import com.example.moviesearch_kotlin.network.OnlineSearchUtil
import kotlinx.coroutines.runBlocking


class SearchActivity : MovieListBaseActivity() {
    override var movies: MutableList<Movie> = mutableListOf()
    var page: Int = 0
    val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        query = intent.getStringExtra("query") ?: ""
        setContent {
            navController = rememberNavController()
            ListMovie()
        }
    }

    var query: String = ""
    override fun loadMore(stopLoading: () -> Unit): Unit = runBlocking {
        OnlineSearchUtil.stopLoading = stopLoading
        try {
            movies += OnlineSearchUtil.searchMoviesByPage(query, page + 1)
            page++;
            Log.d("tag", "Load movies")
        } catch (e: Exception) {
            Toast.makeText(context, "未找到电影", Toast.LENGTH_SHORT).show()
            Log.d("tag", "Error: ${e.message}")
            stopLoading()
        }
    }
}




