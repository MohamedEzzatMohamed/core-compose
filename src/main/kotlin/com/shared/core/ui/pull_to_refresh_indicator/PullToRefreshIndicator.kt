package com.shared.core.ui.pull_to_refresh_indicator

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoxScope.PullToRefreshIndicator(
    isRefreshing: Boolean,
    state: PullToRefreshState,
    modifier: Modifier = Modifier,
    containerColor: Color = Color(0xFFEBF1FC),
    color: Color = Color(0xFF366EE3),
) {
    Indicator(
        modifier = modifier.align(Alignment.TopCenter),
        isRefreshing = isRefreshing,
        state = state,
        containerColor = containerColor,
        color = color
    )
}