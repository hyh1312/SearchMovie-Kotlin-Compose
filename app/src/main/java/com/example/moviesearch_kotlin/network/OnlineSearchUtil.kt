package com.example.moviesearch_kotlin.network

import com.example.moviesearch_kotlin.model.Detail
import com.example.moviesearch_kotlin.model.Movie
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

object OnlineSearchUtil {
    private const val URL = "https://www.omdbapi.com/?apikey=858fc655"
    private val client = OkHttpClient()


    private suspend fun fetch(url: String): Response = suspendCancellableCoroutine { cont ->
        val request = Request.Builder().url(url).get().build()
        val call = client.newCall(request)
        cont.invokeOnCancellation { call.cancel() }

        call.enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: java.io.IOException) {
                cont.resumeWithException(e)
            }

            override fun onResponse(call: okhttp3.Call, response: Response) {
                if (response.isSuccessful) {
                    cont.resume(response)
                } else {
                    cont.resumeWithException(Exception("HTTP error: ${response.code}"))
                }
            }
        })
    }

    suspend fun searchMoviesByPage(name: String, page: Int): MutableList<Movie> {
        val url = "$URL&s=$name&page=$page"
        val response = fetch(url)
        return parseMovieList(response)
    }

    suspend fun searchDetail(id: String): Detail {
        val url = "$URL&i=$id&plot=full"
        val response = fetch(url)
        return parseDetail(response)
    }

    private fun parseMovieList(response: Response): MutableList<Movie> {
        val jsonData = response.body?.string() ?: throw Exception("Empty response body")
        val jsonObject = JSONObject(jsonData)
        val searchArray = jsonObject.getJSONArray("Search")
        val movieList = mutableListOf<Movie>()

        for (i in 0 until searchArray.length()) {
            val movieObject = searchArray.getJSONObject(i)
            val title = movieObject.getString("Title")
            val year = movieObject.getString("Year")
            val poster = movieObject.getString("Poster")
            val id = movieObject.getString("imdbID")
            movieList.add(Movie(title, year, poster, id))
        }
        return movieList
    }

    private fun parseMovieList_New(response: Response): MutableList<Movie> {
        val jsonData = response.body?.string() ?: throw Exception("Empty response body")
        // val movieList = mutableListOf<Movie>()
        val movie = Json.decodeFromString<Movie>(jsonData)

        return mutableListOf(movie)
    }



    private fun parseDetail(response: Response): Detail {
        val jsonData = response.body?.string() ?: throw Exception("Empty response body")
        val jsonObject = JSONObject(jsonData)
        return Detail(
            jsonObject.optString("Title"),
            jsonObject.optString("Year"),
            jsonObject.optString("Rated"),
            jsonObject.optString("Released"),
            jsonObject.optString("Runtime"),
            jsonObject.optString("Genre"),
            jsonObject.optString("Director"),
            jsonObject.optString("Writer"),
            jsonObject.optString("Actors"),
            jsonObject.optString("Plot"),
            jsonObject.optString("Language"),
            jsonObject.optString("Country"),
            jsonObject.optString("Awards"),
            jsonObject.optString("Poster"),
            jsonObject.optString("Metascore"),
            jsonObject.optString("imdbRating"),
            jsonObject.optString("imdbVotes"),
            jsonObject.optString("imdbID"),
            jsonObject.optString("Type"),
            jsonObject.optString("DVD"),
            jsonObject.optString("BoxOffice"),
            jsonObject.optString("Production"),
            jsonObject.optString("Website")
        )
    }
}