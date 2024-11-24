package com.example.moviesearch_kotlin.network

import android.net.http.NetworkException
import android.util.Log
import com.example.moviesearch_kotlin.model.Detail
import com.example.moviesearch_kotlin.model.Movie
import com.example.moviesearch_kotlin.model.MovieList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
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
    var stopLoading: () -> Unit = { Log.d("tag", "wtf?") }
    val Format = Json { ignoreUnknownKeys = true }

    suspend fun fetch(url: String): String = withContext(Dispatchers.IO) {
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()
        val response: Response = client.newCall(request).execute()

        if (response.isSuccessful) {
            return@withContext response.body?.string() ?: ""
        } else {
            throw Exception("Error: ${response.code}")
        }
    }

    suspend fun searchMoviesByPage(name: String, page: Int): List<Movie> {
        val url = "$URL&s=$name&page=$page"
        val response = fetch(url)
        stopLoading()
        return parseMovieList(response)
    }

    suspend fun searchDetail(id: String): Detail {
        val url = "$URL&i=$id&plot=full"
        val response = fetch(url)
        return parseDetail(response)
    }

    private fun parseMovieList(jsonData: String): List<Movie> {
        val movieList = Format.decodeFromString<MovieList>(jsonData)
        Log.d("tag", movieList.Search.toString())
        return movieList.Search
    }


    private fun parseDetail(jsonData: String): Detail {
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
