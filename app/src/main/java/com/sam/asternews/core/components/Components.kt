package com.sam.asternews.core.components

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sam.asternews.R
import com.sam.asternews.core.domain.model.SavedNews
import com.sam.asternews.feature_home.domain.model.CategoryArticle
import com.sam.asternews.feature_home.presentation.HomeScreenViewModel
import com.sam.asternews.ui.theme.MediumBlue
import com.sam.asternews.ui.theme.roboto
import com.sam.asternews.utils.description

@Composable
fun ChipRow(
    modifier: Modifier = Modifier,
    categoryChosen: (String) -> Unit
) {
    val chipList = listOf(
        "Android",
        "Sports",
        "World",
        "iPhone",
        "Russia"
    )
    var selected by rememberSaveable {
        mutableStateOf("Android")
    }
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        contentPadding = PaddingValues(horizontal = 12.dp)
    ) {
        items(chipList) {
            Chip(
                title = it,
                selected = selected,
            ) { title ->
                categoryChosen(title)
                selected = title
            }
        }
    }
}

@Composable
fun Chip(
    title: String,
    selected: String,
    onSelection: (String) -> Unit = {}
) {
    val isSelected = selected == title
    val background = if (isSelected) MediumBlue else Color.White
    val contentColor = if (isSelected) Color.White else Color.Black
    Surface(
        modifier = Modifier
            .height(32.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable {
                onSelection(title)
            },
        color = background,
    ) {
        Row(
            modifier = Modifier.fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 18.dp),
                text = title,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1.copy(
                    color = contentColor,
                    fontFamily = roboto,
                    fontSize = 15.sp
                )
            )
        }
    }
}

@Composable
fun NewsCard(
    modifier: Modifier = Modifier,
    article: CategoryArticle,
    viewModel: HomeScreenViewModel
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
                if (article.urlToImage != null) {
                    Row {
                        AsyncImage(
                            modifier = Modifier
                                .size(width = 120.dp, height = 130.dp)
                                .clip(MaterialTheme.shapes.medium),
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(article.urlToImage)
                                .crossfade(true)
                                .build(),
                            placeholder = painterResource(id = R.drawable.placeholder),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    }
                }
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
                                val news = SavedNews(
                                    title = article.title,
                                    description = article.description,
                                    content = article.content
                                )
                                viewModel.showSnackBar("News Successfully Saved","Undo")
                                viewModel.saveNews(news)
                            },
                        painter = painterResource(id = R.drawable.save),
                        contentDescription = "Save"
                    )
                }
            }
        }
    }
}

@Composable
fun NewsTitleDescription(modifier: Modifier = Modifier, article: CategoryArticle) {
    Column(modifier = modifier) {
        Text(
            text = article.title,
            style = MaterialTheme.typography.h5.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp
            )
        )
        Text(
            text = description(article.description),
            style = MaterialTheme.typography.h5.copy(
                fontSize = 14.sp
            )
        )
    }
}
