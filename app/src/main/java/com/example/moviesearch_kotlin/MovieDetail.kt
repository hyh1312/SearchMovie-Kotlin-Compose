package com.example.moviesearch_kotlin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import androidx.compose.material3.*
import androidx.compose.ui.draw.clip

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MovieInfoScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieInfoScreen() {
    val moviePoster = painterResource(id = R.drawable.ic_launcher_foreground) // 示例图片
    val title = "Title"
    val yearRatedRuntime = "Year, Rated, Runtime"
    val genre = "Genre"
    val plot = "Movie plot goes here."
    val directorWriterCast = "Director, Writer, and Cast details."
    val languageCountry = "Language & Country details."
    val boxOfficeAwards = "Box Office & Awards details."
    val imdbRatingVotes = "IMDb Rating & Votes details."
    val productionWebsite = "Production & Website details."

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
                        contentDescription = "Back"
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

        // Loading spinner (ProgressBar)
        CircularProgressIndicator(
            modifier = Modifier
                .size(60.dp)
                .align(Alignment.Center)
        )

        // Scrollable content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .verticalScroll(rememberScrollState())
                .padding(top = 56.dp) // To offset the toolbar height
        ) {
            // Movie Poster
            Image(
                painter = moviePoster,
                contentDescription = "Movie Poster",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            // Movie Title
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(top = 16.dp)
            )

            // Year, Rated, Runtime
            Text(
                text = yearRatedRuntime,
                style = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray),
                modifier = Modifier.padding(top = 8.dp)
            )

            // Genre
            Text(
                text = genre,
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
                text = plot,
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
                text = directorWriterCast,
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
                text = languageCountry,
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
                text = boxOfficeAwards,
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
                text = imdbRatingVotes,
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
                text = productionWebsite,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
