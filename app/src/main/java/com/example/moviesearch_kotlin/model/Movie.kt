package com.example.moviesearch_kotlin.model

////import androidx.room.Entity
////import androidx.room.PrimaryKey
//
//@Entity(tableName = "movie_table")
data class Movie(
    val title: String,
    val year: String,
    val posterUrl: String,
    var id: String
)