package com.example.moviesearch_kotlin.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "movie_table")
@Serializable
data class Movie(
    @PrimaryKey val imdbID: String,
    val Title: String,
    val Year: String,
    val Poster: String,
)

@Serializable
data class MovieList(
    val Search: List<Movie>,
)