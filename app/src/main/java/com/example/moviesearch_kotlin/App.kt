package com.example.moviesearch_kotlin

import android.app.Application
import androidx.room.Room.databaseBuilder
import com.example.moviesearch_kotlin.model.MovieRoomDatabase

class App : Application() {
    companion object {
        lateinit var db: MovieRoomDatabase
    }

    override fun onCreate() {
        super.onCreate()
        db = databaseBuilder(
            applicationContext,
            MovieRoomDatabase::class.java, "movie_database"
        ).build()
    }
}