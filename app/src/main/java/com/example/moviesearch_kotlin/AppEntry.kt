package com.example.moviesearch_kotlin

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.moviesearch_kotlin.ui.HomePage
import com.example.moviesearch_kotlin.ui.ListDetail
import com.example.moviesearch_kotlin.ui.ListMovies
import kotlinx.serialization.Serializable

@Serializable
object HomeRoute

@Serializable
data class SearchResult(val query: String)

@Serializable
data class MovieDetail(val query: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppEntry(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = HomeRoute) {
        composable<HomeRoute> {
            HomePage { query ->
                navController.navigate(route = SearchResult(query))
            }
        }
        composable<SearchResult> { backStackEntry ->
            val searchList: SearchResult = backStackEntry.toRoute()
            ListMovies(
                query = searchList.query,
                { query -> navController.navigate(route = MovieDetail(query)) }
            ) { navController.navigateUp() }

        }
        composable<MovieDetail> { backStackEntry ->
            val detailList: MovieDetail = backStackEntry.toRoute()
            ListDetail(detailList.query) { navController.navigateUp() }
        }
    }
}