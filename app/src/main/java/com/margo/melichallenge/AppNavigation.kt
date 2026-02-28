package com.margo.melichallenge

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.margo.news_detail.presentation.NewsDetailScreen
import com.margo.news_feed.presentation.NewsFeedRoute

@Composable
fun SpaceFlightNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "news_feed"
    ) {
        composable(route = "news_feed") {
            NewsFeedRoute(
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
        ) {
            NewsDetailScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}