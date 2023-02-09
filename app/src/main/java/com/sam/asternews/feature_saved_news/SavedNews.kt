package com.sam.asternews.feature_saved_news

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sam.asternews.R
import com.sam.asternews.core.domain.model.SavedNews
import com.sam.asternews.core.domain.model.savedNewsExample
import com.sam.asternews.feature_home.presentation.HomeScreenViewModel
import com.sam.asternews.ui.theme.roboto
import com.sam.asternews.utils.OneTimeEvents
import com.sam.asternews.utils.ROUTES
import com.sam.asternews.utils.description


@Composable
fun SavedScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val savedNews = viewModel.savedNews.collectAsState(initial = emptyList())
    LaunchedEffect(key1 = true) {
        viewModel.uiState.collect { event ->
            when (event) {
                is OneTimeEvents.Navigation -> {}
                is OneTimeEvents.PopBackStack -> {
                    navController.popBackStack()
                }
                is OneTimeEvents.SnackBar -> {}
            }
        }
    }
    Scaffold(topBar = {
        TopAppBar(
            modifier = Modifier.fillMaxWidth(),
            elevation = 0.dp,
            backgroundColor = MaterialTheme.colors.secondary
        ) {
            Icon(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .clickable {
                        viewModel.pop()
                    },
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(id = R.string.back)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.saved_news),
                    style = MaterialTheme.typography.h6.copy(
                        fontFamily = roboto,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }) {
        LazyColumn(modifier = Modifier.padding(it)) {
            items(savedNews.value) { news ->
                SavedNewsCard(
                    news = news,
                    viewModel = viewModel
                )
            }
        }
    }
}

@Composable
fun SavedNewsCard(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel,
    news: SavedNews = savedNewsExample
) {
    Surface(
        modifier = modifier
            .padding(12.dp),
        elevation = 4.dp,
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            SavedNewsTitleDescription(news = news)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    modifier = Modifier.clickable { viewModel.deleteNews(news) },
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(id = R.string.delete)
                )
            }
        }
    }
}

@Composable
fun SavedNewsTitleDescription(
    modifier: Modifier = Modifier,
    news: SavedNews
) {
    Column(modifier = modifier) {
        Text(
            text = news.title,
            style = MaterialTheme.typography.h5.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp
            )
        )
        Text(
            text = description(news.description),
            style = MaterialTheme.typography.h5.copy(
                fontSize = 14.sp
            )
        )
    }
}
