package com.example.moviesearch_kotlin.model

import android.content.Context
import androidx.lifecycle.LiveData

class MovieRepository(context: Context) {
    private val mMovieDao: MovieDao

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allMovies: LiveData<List<Movie>>

    // Note that in order to unit test the MovieRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    init {
        val db = MovieRoomDatabase.getDatabase(context)
        mMovieDao = db!!.movieDao()
        allMovies = mMovieDao.getAll()
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    fun insert(movie: Movie) {
        MovieRoomDatabase.databaseWriteExecutor.execute {
            mMovieDao.insert(movie)
        }
    }

    fun deleteAll() {
        MovieRoomDatabase.databaseWriteExecutor.execute {
            mMovieDao.deleteAll()
        }
    }
}
