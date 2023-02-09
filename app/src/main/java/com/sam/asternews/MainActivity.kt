package com.sam.asternews

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.Navigation
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.sam.asternews.feature_home.presentation.HomeScreen
import com.sam.asternews.feature_home.presentation.HomeScreenViewModel
import com.sam.asternews.feature_top_headline.presentation.TopHeadlines
import com.sam.asternews.ui.theme.AsterNewsTheme
import com.sam.asternews.ui.theme.LightBlue
import com.sam.asternews.ui.theme.MediumBlue
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showSystemUI()
        setContent {
            val viewModel by viewModels<HomeScreenViewModel>()
            MyApp (viewModel = viewModel){
             Navigation()
            }
        }
    }
    private fun showSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }
}

@Composable
fun MyApp(viewModel: HomeScreenViewModel,content:@Composable ()->Unit) {
    val systemUiController = rememberSystemUiController()
    AsterNewsTheme(themeState = viewModel.themeState.collectAsState().value) {
        DisposableEffect(systemUiController, true){
            systemUiController.setStatusBarColor(
                color = Color.Transparent,
                darkIcons = true
            )
            onDispose {  }
        }
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.secondary
        ) {
            Column(modifier = Modifier
                .fillMaxHeight()
                .padding(top = 35.dp)){
                content()
            }
        }
    }
}

