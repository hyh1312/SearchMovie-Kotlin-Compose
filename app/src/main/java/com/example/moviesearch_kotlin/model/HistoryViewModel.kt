package com.example.moviesearch_kotlin.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class HistoryViewModel(application: Application) : AndroidViewModel(application) {
    private val mRepository = MovieRepository(application)

    val allMovies: LiveData<List<Movie>> =
        mRepository.allMovies

    fun insert(Movie: Movie) {
        mRepository.insert(Movie)
    }

    fun deleteAll() {
        mRepository.deleteAll()
    }
}