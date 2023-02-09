package com.sam.asternews

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sam.asternews.feature_home.presentation.HomeScreen
import com.sam.asternews.feature_saved_news.SavedScreen
import com.sam.asternews.feature_top_headline.presentation.TopHeadlines
import com.sam.asternews.utils.ROUTES


@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ROUTES.HOME){
        composable(ROUTES.HOME){
            HomeScreen(navController = navController)
        }
        composable(ROUTES.TOP_HEADLINES){
            TopHeadlines(navController = navController)
        }
        composable(ROUTES.SAVED){
            SavedScreen(navController = navController)
        }
    }
}