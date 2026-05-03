package com.shared.core.ui.pager_content

import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PagerContent(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    snapPosition: SnapPosition = SnapPosition.Start,
    pageNumber: Int,
    content: @Composable (page: Int) -> Unit
) {
    val state = rememberPagerState(initialPage = 0, pageCount = { maxOf(a = pageNumber, b = 1) })
    HorizontalPager(
        state = state,
        modifier = modifier,
        contentPadding = contentPadding,
        snapPosition = snapPosition
    ) { page -> content(page) }
}