package com.example.moviesearch_kotlin.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moviesearch_kotlin.App

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MovieRoomDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}

val db = App.db
val dao = db.movieDao()

