package com.margo.melichallenge

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.margo.news_detail.presentation.NewsDetailRoute
import com.margo.news_feed.presentation.NewsFeedRoute

/**
 * Sets up the main navigation graph for the Space Flight application.
 * It defines the destinations and arguments needed to navigate between the news feed and news detail screens.
 */
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
            NewsDetailRoute(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}