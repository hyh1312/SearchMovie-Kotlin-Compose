package com.example.moviesearch_kotlin.model

import kotlinx.serialization.Serializable

@Serializable
data class Rating(
    val Source: String,
    val Value: String,
)

@Serializable
data class Detail(
    val Title: String,
    val Year: String,
    val Rated: String,
    val Released: String,
    val Runtime: String,
    val Genre: String,
    val Director: String,
    val Writer: String,
    val Actors: String,
    val Plot: String,
    val Language: String,
    val Country: String,
    val Awards: String,
    val Poster: String,
    val Ratings: List<Rating>,
    val Metascore: String,
    val imdbRating: String,
    val imdbVotes: String,
    val imdbID: String,
    val Type: String,
    val Dvd: String,
    val BoxOffice: String,
    val Production: String,
    val Website: String,
)