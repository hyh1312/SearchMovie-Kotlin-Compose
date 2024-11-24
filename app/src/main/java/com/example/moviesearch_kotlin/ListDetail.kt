package com.example.moviesearch_kotlin

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.moviesearch_kotlin.R.drawable.ic_launcher_foreground
import com.example.moviesearch_kotlin.model.Detail
import com.example.moviesearch_kotlin.network.OnlineSearchUtil
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ListDetail(id: String) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(true) }
    if (isLoading) CircularProgress()
    var detail by remember { mutableStateOf(Detail()) }
    coroutineScope.launch {
        try {
            detail = OnlineSearchUtil.searchDetail(id) { isLoading = false }
            Log.d("tag", detail.toString())
        } catch (e: Exception) {
            Toast.makeText(context, "出现错误", Toast.LENGTH_LONG).show()
            Log.d("tag", "Error: $e")
            isLoading = false
        }
    }
    ShowDetail(detail)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowDetail(detail: Detail) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Toolbar
        TopAppBar(
            title = {
                Text(text = "电影信息", color = Color.White)
            },
            modifier = Modifier.fillMaxWidth(),
            navigationIcon = {
                // 可以在这里添加导航图标，如返回按钮
                IconButton(onClick = { /* Do something */ }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            },
            actions = {
                // 这里可以添加操作按钮
                IconButton(onClick = { /* Do something */ }) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = "More")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
            scrollBehavior = null // 可根据需要使用滚动行为
        )

        // Scrollable content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .verticalScroll(rememberScrollState())
                .padding(top = 56.dp) // To offset the toolbar height
        ) {
            // Movie Poster (这里你可以根据 Poster 的 URL 加载图片)
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
}