package com.shared.core.ui.paging_items_content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems

@Composable
fun <T : Any> PagingLazyColumnContent(
    modifier: Modifier = Modifier,
    pagingItems: LazyPagingItems<T>,
    item: @Composable LazyItemScope.(T) -> Unit,
    onFirstLoadError: @Composable () -> Unit = {},
    onFirstLoadLoading: @Composable () -> Unit = {},
    onLoadMoreError: @Composable () -> Unit = {},
    onLoadMoreLoading: @Composable () -> Unit = {},
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
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