package com.example.moviesearch_kotlin.model

import kotlinx.serialization.Serializable

////import androidx.room.Entity
////import androidx.room.PrimaryKey
//
//@Entity(tableName = "movie_table")

@Serializable
data class Movie(
    val Title: String,
    val Year: String,
    val Poster: String,
    val imdbID: String,
)

@Serializable
data class MovieList(
    val Search: List<Movie>,
)