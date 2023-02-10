package com.sam.asternews.feature_home.presentation


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sam.asternews.R
import com.sam.asternews.core.components.ChipRow
import com.sam.asternews.core.components.NewsCard
import com.sam.asternews.feature_home.domain.model.CategoryNews
import com.sam.asternews.ui.theme.MediumBlue
import com.sam.asternews.ui.theme.roboto
import com.sam.asternews.utils.OneTimeEvents
import com.sam.asternews.utils.ROUTES
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(viewModel: HomeScreenViewModel = hiltViewModel(),navController: NavController) {
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true){
        viewModel.uiState.collect{event ->
            when(event){
                is OneTimeEvents.Navigation ->{
                        scaffoldState.drawerState.close()
                        navController.navigate(event.route)
                }
                is OneTimeEvents.PopBackStack ->{}
                is OneTimeEvents.SnackBar ->{
                   scaffoldState.snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }
    Scaffold(
        topBar = {
            TopBar(scaffoldState = scaffoldState)
        },
        scaffoldState = scaffoldState,
        drawerContent = {
            Drawer(viewModel = viewModel)
        },
        drawerShape = NavShape(0.dp,0.95f)
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            ChipRow(
                modifier = Modifier
                    .background(MaterialTheme.colors.background)
                    .padding(vertical = 15.dp)
            ) { category ->
                viewModel.getCategoryNews(category)
            }
            when (val data = viewModel.state.value) {
                is HomeScreenUiState.Loading -> {
                    Loading()
                }
                is HomeScreenUiState.Error -> {
                    HeadLineScreenError()
                }
                is HomeScreenUiState.Success -> {
                    HomeScreen(data.data,viewModel)
                }
            }
        }
    }
}



@Composable
fun HomeScreen(category: CategoryNews, viewModel: HomeScreenViewModel) {
    LazyColumn {
        items(category.articles) { article ->
            NewsCard(article = article, viewModel = viewModel)
        }
    }
}


@Composable
fun TopBar(modifier: Modifier = Modifier,
           scaffoldState: ScaffoldState) {
    val scope = rememberCoroutineScope()
    TopAppBar(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.secondary)
            .padding(horizontal = 5.dp),
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colors.secondary
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(25.dp)
                        .clickable {
                            scope.launch {
                                scaffoldState.drawerState.open()
                            }
                        },
                    painter = painterResource(id = R.drawable.menu),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(18.dp))
                Image(
                    modifier = Modifier.size(25.dp),
                    painter = painterResource(id = R.drawable.img),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(18.dp))
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.h5.copy(
                        fontFamily = roboto,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}


@Composable
fun Loading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = MediumBlue)
    }
}

@Composable
fun HeadLineScreenError() {
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center){
        Image(painter = painterResource(id = R.drawable.error),
            contentDescription = stringResource(id = R.string.error))
    }
}

