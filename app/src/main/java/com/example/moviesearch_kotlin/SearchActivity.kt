package com.example.moviesearch_kotlin

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import com.example.moviesearch_kotlin.model.Movie
import com.example.moviesearch_kotlin.network.OnlineSearchUtil
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class SearchActivity : MovieListBaseActivity() {
    override var movies: MutableList<Movie> = mutableListOf()
    var page:Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        query = intent.getStringExtra("query") ?: ""
        GetMovies()
        setContent{
            ListMovie()
        }
    }
    var query: String = ""
    fun GetMovies() = runBlocking {
        try{
            movies = OnlineSearchUtil.searchMoviesByPage(query,page)
        } catch (e: Exception) {
            Log.d("TAG","Error: ${e.message}")
        }
    }
}




