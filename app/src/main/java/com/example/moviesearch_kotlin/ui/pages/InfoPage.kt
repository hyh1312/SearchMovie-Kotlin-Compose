package com.example.moviesearch_kotlin.ui.pages

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.moviesearch_kotlin.model.Detail
import com.example.moviesearch_kotlin.network.OnlineSearchUtil
import com.example.moviesearch_kotlin.ui.AppBar
import com.example.moviesearch_kotlin.ui.CircularProgress

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun InfoPage(id: String, goBack: () -> Unit) {
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(true) }
    var detail by remember { mutableStateOf(Detail()) }

    LaunchedEffect(true) {
        try {
            detail = OnlineSearchUtil.searchDetail(id) { isLoading = false }

        } catch (e: Exception) {
            Toast.makeText(context, "出现错误", Toast.LENGTH_SHORT).show()
            Log.d("tag", "Error: $e")
            isLoading = false
        }
    }

    AppBar("电影详情", goBack) { innerPadding ->
        ShowDetail(innerPadding, detail)
        if (isLoading) CircularProgress()
    }

}

@Composable
fun ShowDetail(innerPadding: PaddingValues, detail: Detail) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Movie Poster
        Image(
            painter = rememberAsyncImagePainter(detail.Poster),
            contentDescription = "Movie Poster",
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        // Movie Title
        Text(
            text = detail.Title,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = 16.dp)
        )

        // Year, Rated, Runtime
        Text(
            text = "${detail.Year}, ${detail.Rated}, ${detail.Runtime}",
            style = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray),
            modifier = Modifier.padding(top = 8.dp)
        )

        // Genre
        Text(
            text = detail.Genre,
            style = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray),
            modifier = Modifier.padding(top = 8.dp)
        )

        // Plot Header
        Text(
            text = "Plot",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = 16.dp)
        )

        // Plot Description
        Text(
            text = detail.Plot,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 8.dp)
        )

        // Director, Writer & Cast Header
        Text(
            text = "Director, Writer & Cast",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = 16.dp)
        )

        // Director, Writer & Cast Description
        Text(
            text = "Director: ${detail.Director}\nWriter: ${detail.Writer}\nCast: ${detail.Actors}",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 8.dp)
        )

        // Language & Country Header
        Text(
            text = "Language & Country",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = 16.dp)
        )

        // Language & Country Description
        Text(
            text = "${detail.Language}, ${detail.Country}",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 8.dp)
        )

        // Box Office & Awards Header
        Text(
            text = "Box Office & Awards",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = 16.dp)
        )

        // Box Office & Awards Description
        Text(
            text = "Box Office: ${detail.BoxOffice}\nAwards: ${detail.Awards}",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 8.dp)
        )

        // IMDb Rating & Votes Header
        Text(
            text = "IMDb Rating & Votes",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = 16.dp)
        )

        // IMDb Rating & Votes Description
        Text(
            text = "Rating: ${detail.imdbRating}\nVotes: ${detail.imdbVotes}",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 8.dp)
        )

        // Production & Website Header
        Text(
            text = "Production & Website",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = 16.dp)
        )

        // Production & Website Description
        Text(
            text = "Production: ${detail.Production}\nWebsite: ${detail.Website}",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}