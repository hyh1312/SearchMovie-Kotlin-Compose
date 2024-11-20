package com.example.moviesearch_kotlin

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
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
            ListMovie()
        }
    }

    var query: String = ""
    override fun loadMore(): Unit = runBlocking {
        try {
            movies += OnlineSearchUtil.searchMoviesByPage(query, page + 1)
            page++;
        } catch (e: Exception) {
            Toast.makeText(context, "bottom", Toast.LENGTH_SHORT).show()
            Log.d("TAG", "Error: ${e.message}")
        }
    }
}




