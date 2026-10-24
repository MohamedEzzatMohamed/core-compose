package com.shared.core.ui.app_top_bar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AppTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    TopAppBar(
        colors = appBarColors(),
        title = { TopAppBarTitle(title) },
        navigationIcon = navigationIcon,
        actions = actions,
        scrollBehavior = scrollBehavior,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun appBarColors() = TopAppBarDefaults.topAppBarColors(
    containerColor = Color(0xFFFFFFFC),
    scrolledContainerColor = Color(0xFFE6E6E3),
    navigationIconContentColor = Color(0xFF000000),
    titleContentColor = Color(0xFF000000),
    actionIconContentColor = Color(0xFF000000)
)

@Composable
fun TopAppBarTitle(title: String) {
    Text(
        text = title,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.SemiBold,
            fontSize = 22.sp
        )
    )
}