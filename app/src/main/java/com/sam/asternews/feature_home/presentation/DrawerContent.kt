package com.sam.asternews.feature_home.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sam.asternews.R
import com.sam.asternews.core.ThemeState
import com.sam.asternews.ui.theme.AsterNewsThemeSettings
import com.sam.asternews.ui.theme.MediumBlue
import com.sam.asternews.ui.theme.roboto
import com.sam.asternews.utils.ROUTES


@Composable
fun DrawerItem(
    modifier: Modifier = Modifier,
    drawerContent: DrawerContent,
    navigate: (String) -> Unit = {}
) {
    Row(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth()
            .height(30.dp)
            .clickable {
                navigate(drawerContent.route)
            },
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(25.dp),
            painter = painterResource(id = drawerContent.icon),
            contentDescription = drawerContent.text
        )
        Text(
            text = drawerContent.text,
            style = MaterialTheme.typography.body2.copy(
                fontFamily = roboto,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
fun ThemeSwitch(modifier: Modifier = Modifier,
                viewModel: HomeScreenViewModel,
                themeState: ThemeState
) {

    val isDarkTheme = themeState.isDarkTheme
    Row(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth()
            .height(30.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.moon),
            contentDescription = stringResource(id = R.string.theme)
        )
        Text(
            text = stringResource(id = R.string.dark_theme),
            style = MaterialTheme.typography.body2.copy(
                fontFamily = roboto,
                fontWeight = FontWeight.Bold
            )
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Switch(checked = isDarkTheme,
                onCheckedChange = {
                    viewModel.setTheme(it)
                }
            )
        }
    }
}

@Preview
@Composable
fun DrawerTitle(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.img),
            contentDescription = stringResource(id = R.string.title)
        )
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.h5.copy(
                color = MediumBlue,
                fontFamily = roboto,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
fun Drawer(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            DrawerTitle()
        }
        items(drawerItems) {
            DrawerItem(drawerContent = it) { route ->
                viewModel.navigate(route)
            }
        }
        item {
            ThemeSwitch(viewModel = viewModel,
                themeState = viewModel.themeState.collectAsState().value)
        }
    }
}


data class DrawerContent(
    val icon: Int,
    val text: String,
    val route: String
)

val drawerItems = listOf(
    DrawerContent(R.drawable.home, "Top Stories", ROUTES.TOP_HEADLINES),
    DrawerContent(R.drawable.setting, "Saved News", ROUTES.SAVED)
)