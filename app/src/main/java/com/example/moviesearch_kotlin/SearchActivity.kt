package com.example.moviesearch_kotlin

import android.os.Bundle
import com.example.moviesearch_kotlin.model.Movie


class SearchActivity : MovieListBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        query = intent.getStringExtra("query") ?: ""
        GetMovies()
    }
}


fun GetMovies() {

}

var query: String? = ""


