package com.example.moviesearch_kotlin

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
object HomeRoute

@Serializable
data class SearchList(val query: String)

@Serializable
data class DetailList(val query: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppEntry(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = HomeRoute) {
        composable<HomeRoute> {
            HomePage { query ->
                navController.navigate(route = SearchList(query))
            }
        }
        composable<SearchList> { backStackEntry ->
            val searchList: SearchList = backStackEntry.toRoute()
            ListMovie(query = searchList.query) { query ->
                navController.navigate(route = DetailList(query))
            }
        }
        composable<DetailList> { backStackEntry ->
            val detailList: DetailList = backStackEntry.toRoute()
            ListDetail(detailList.query)
        }
    }
}