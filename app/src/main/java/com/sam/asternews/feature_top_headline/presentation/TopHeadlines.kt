package com.sam.asternews.feature_top_headline.presentation


import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sam.asternews.R
import com.sam.asternews.core.domain.model.SavedNews
import com.sam.asternews.feature_top_headline.domain.model.Article
import com.sam.asternews.feature_top_headline.domain.model.TopHeadlines
import com.sam.asternews.feature_top_headline.domain.model.articleExample
import com.sam.asternews.ui.theme.MediumBlue
import com.sam.asternews.ui.theme.roboto
import com.sam.asternews.utils.OneTimeEvents

@Composable
fun TopHeadlines(
    viewModel: TopHeadlinesViewModel = hiltViewModel(),
    navController: NavController
) {
    LaunchedEffect(key1 = true){
        viewModel.uiState.collect{ event ->
            when(event){
                is OneTimeEvents.Navigation -> {

                }
                is OneTimeEvents.PopBackStack -> {
                    navController.popBackStack()
                }
                is OneTimeEvents.SnackBar -> {

                }
            }
        }
    }
    Scaffold(
        topBar = {
            TopBar(viewModel)
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            when (val data = viewModel.state.value) {
                is TopHeadlinesUiState.Loading -> {
                    TopHeadlinesScreenLoading()
                }
                is TopHeadlinesUiState.Error -> {
                    TopHeadlinesScreenError()
                }
                is TopHeadlinesUiState.Success ->
                    TopHeadlines(topHeadline = data.data, viewModel = viewModel)
            }
        }
    }
}


@Composable
fun TopHeadlines(
    modifier: Modifier = Modifier,
    topHeadline: TopHeadlines,
    viewModel: TopHeadlinesViewModel
) {
    val articles = topHeadline.articles
    Headlines(articles = articles, viewModel = viewModel)
}

@Composable
fun Headlines(
    modifier: Modifier = Modifier,
    articles: List<Article>,
    viewModel: TopHeadlinesViewModel
) {
    LazyColumn(modifier = modifier.padding(2.dp)) {
        items(articles) { article ->
            NewsCard(article = article, viewModel = viewModel)
        }
    }
}


//@Preview(showBackground = true)
@Composable
fun NewsCard(
    modifier: Modifier = Modifier,
    article: Article = articleExample,
    viewModel: TopHeadlinesViewModel
) {
    val dateTime = article.publishedAt.split("T")
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current
    var expanded by remember {
        mutableStateOf(false)
    }
    Surface(
        modifier = modifier
            .padding(12.dp)
            .clickable {
                expanded = !expanded
            },
        elevation = 4.dp,
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row {
                Row(modifier = Modifier.weight(1f)) {
                    NewsTitleDescription(
                        modifier = Modifier.padding(4.dp),
                        article = article
                    )
                }
                Row {
                    AsyncImage(
                        modifier = Modifier
                            .size(width = 120.dp, height = 130.dp)
                            .clip(MaterialTheme.shapes.medium),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(article.urlToImage)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                Row(modifier = Modifier.weight(1f)) {
                    Text(
                        text = article.author, style = MaterialTheme.typography.caption.copy(
                            fontFamily = roboto,
                            color = Color.Gray
                        )
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = dateTime[0],
                        style = MaterialTheme.typography.caption.copy(
                            fontFamily = roboto,
                            color = Color.Gray
                        )
                    )
                }
                Text(
                    text = article.source.id,
                    style = MaterialTheme.typography.caption.copy(
                        fontFamily = roboto,
                        color = Color.Gray
                    )
                )
            }
            AnimatedVisibility(visible = expanded) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .size(25.dp)
                            .clickable {
                                try {
                                    uriHandler.openUri(article.url)
                                } catch (e: Exception) {
                                    Toast
                                        .makeText(context, e.localizedMessage, Toast.LENGTH_LONG)
                                        .show()
                                }
                            },
                        painter = painterResource(id = R.drawable.read_more),
                        contentDescription = "Read more"
                    )
                    Icon(
                        modifier = modifier
                            .size(18.dp)
                            .clickable {
                                viewModel.saveNews(
                                    SavedNews(
                                        title = article.title,
                                        description = article.description,
                                        content = article.content
                                    )
                                )
                            },
                        painter = painterResource(id = R.drawable.save),
                        contentDescription = "Save"
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun NewsTitleDescription(modifier: Modifier = Modifier, article: Article = articleExample) {
    Column(modifier = modifier) {
        Text(
            text = article.title,
            style = MaterialTheme.typography.h5.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp
            )
        )
        Text(
            text = article.description,
            style = MaterialTheme.typography.h5.copy(
                fontSize = 14.sp
            )
        )
    }
}

@Composable
fun TopHeadlinesScreenLoading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(0.5.dp),
            color = MediumBlue
        )
    }
}

@Composable
fun TopHeadlinesScreenError() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .size(150.dp),
            painter = painterResource(id = R.drawable.error),
            contentDescription = stringResource(id = R.string.error)
        )
    }
}

@Composable
fun TopBar(viewModel: TopHeadlinesViewModel) {
    TopAppBar(
        modifier = Modifier
            .background(MaterialTheme.colors.secondary)
            .padding(horizontal = 4.dp),
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colors.secondary
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(25.dp)
                    .clickable {
                        viewModel.pop()
                    },
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(id = R.string.back)
            )
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier,
                    text = "Trending News",
                    style = MaterialTheme.typography.h6.copy(
                        fontFamily = roboto,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}