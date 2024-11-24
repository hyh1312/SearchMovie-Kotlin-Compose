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
    val Format = Json { ignoreUnknownKeys = true }

    private suspend fun fetch(url: String): String = withContext(Dispatchers.IO) {
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()
        val response: Response = client.newCall(request).execute()

        if (response.isSuccessful) {
            return@withContext response.body?.string() ?: ""
        } else {
            throw Exception("Error: ${response.code}")
        }
    }

    // val exampleMovie = "https://www.omdbapi.com/?apikey=858fc655&s=123"
    suspend fun searchMoviesByPage(name: String, page: Int, stopLoading: () -> Unit): List<Movie> {
        val url = "$URL&s=$name&page=$page"
        val response = fetch(url)
        stopLoading()
        return parseMovieList(response)
    }

    // val exampleDetail = "https://www.omdbapi.com/?apikey=858fc655&i=tt1111422&plot=full"
    suspend fun searchDetail(id: String, stopLoading: () -> Unit): Detail {
        val url = "$URL&i=$id&plot=full"
        val response = fetch(url)
        stopLoading()
        return parseDetail(response)
    }

    private fun parseMovieList(jsonData: String): List<Movie> {
        val movieList = Format.decodeFromString<MovieList>(jsonData)
        // Log.d("tag", movieList.Search.toString())
        return movieList.Search
    }

    private fun parseDetail(jsonData: String): Detail {
        val detail = Format.decodeFromString<Detail>(jsonData)
        return detail
    }
}
