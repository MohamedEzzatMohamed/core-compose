package com.shared.core.ui.paging_items_content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems

@Composable
fun <T : Any> PagingLazyVerticalGridContent(
    modifier: Modifier = Modifier,
    columns: GridCells,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    pagingItems: LazyPagingItems<T>,
    item: @Composable LazyGridItemScope.(T) -> Unit,
    onFirstLoadError: @Composable () -> Unit = {},
    onFirstLoadLoading: @Composable () -> Unit = {},
    onLoadMoreError: @Composable () -> Unit = {},
    onLoadMoreLoading: @Composable () -> Unit = {},
) {
    LazyVerticalGrid(
        columns = columns,
        horizontalArrangement = horizontalArrangement,
        verticalArrangement = verticalArrangement,
        modifier = modifier
    ) {

        items(
            count = pagingItems.itemCount,
            key = { index -> pagingItems[index]?.hashCode() ?: index }
        ) { index ->
            pagingItems[index]?.let { item(it) }
        }

        pagingItems.loadToFirstItem(
            onError = { item { onFirstLoadError() } },
            onLoading = { item { onFirstLoadLoading() } }
        )

        pagingItems.loadToMoreItem(
            onError = { item { onLoadMoreError() } },
            onLoading = { item { onLoadMoreLoading() } }
        )
    }
}