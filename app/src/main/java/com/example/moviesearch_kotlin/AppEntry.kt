package com.example.moviesearch_kotlin

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.moviesearch_kotlin.ui.pages.HistoryPage
import com.example.moviesearch_kotlin.ui.pages.HomePage
import com.example.moviesearch_kotlin.ui.pages.InfoPage
import com.example.moviesearch_kotlin.ui.pages.SearchPage
import kotlinx.serialization.Serializable

@Serializable
object HomeRoute

@Serializable
data class SearchPage(val query: String)

@Serializable
data class InfoPage(val query: String)

@Serializable
object HistoryPage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppEntry(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = HomeRoute) {
        composable<HomeRoute> {
            HomePage { query ->
                navController.navigate(route = SearchPage(query))
            }
        }
        composable<SearchPage> { backStackEntry ->
            val searchPage: SearchPage = backStackEntry.toRoute()
            SearchPage(
                query = searchPage.query,
                toDetail = { query -> navController.navigate(route = InfoPage(query)) }
            ) { navController.navigateUp() }

        }
        composable<InfoPage> { backStackEntry ->
            val infoPage: InfoPage = backStackEntry.toRoute()
            InfoPage(infoPage.query) { navController.navigateUp() }
        }
        composable<HistoryPage> { backStackEntry ->
            val historyPage: HistoryPage = backStackEntry.toRoute()
            HistoryPage(toDetail = { query -> navController.navigate(route = InfoPage(query)) }
            ) { navController.navigateUp() }
        }
    }
}