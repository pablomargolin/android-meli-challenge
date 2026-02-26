package com.margo.melichallenge

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.margo.news_feed.presentation.NewsFeedScreen

@Composable
fun SpaceFlightNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "news_feed"
    ) {
        composable(route = "news_feed") {
            NewsFeedScreen(
                onNavigateToDetail = { articleId ->
                    navController.navigate("news_detail/$articleId")
                }
            )
        }
        composable(
            route = "news_detail/{articleId}",
            arguments = listOf(
                navArgument("articleId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val articleId = backStackEntry.arguments?.getInt("articleId") ?: -1

            // TODO: NewsDetailRoute
            Text("Article ID: $articleId")
        }
    }
}