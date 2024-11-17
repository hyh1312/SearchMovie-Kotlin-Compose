package com.example.moviesearch_kotlin.model

import kotlinx.serialization.Serializable

////import androidx.room.Entity
////import androidx.room.PrimaryKey
//
//@Entity(tableName = "movie_table")

@Serializable
data class Movie(
    val title: String,
    val year: String,
    val posterUrl: String,
    var id: String
)