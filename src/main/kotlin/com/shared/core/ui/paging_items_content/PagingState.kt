package com.shared.core.ui.paging_items_content

import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

internal fun <T : Any> LazyPagingItems<T>.loadToFirstItem(
    onError: () -> Unit,
    onLoading: () -> Unit
) =
    when (this.loadState.refresh) {
        is LoadState.Error -> onError()

        is LoadState.Loading -> onLoading()
        is LoadState.NotLoading -> {}
    }


internal fun <T : Any> LazyPagingItems<T>.loadToMoreItem(
    onError: () -> Unit,
    onLoading: () -> Unit
) =
    when (this.loadState.append) {
        is LoadState.Error -> onError()

        is LoadState.Loading -> onLoading()
        is LoadState.NotLoading -> {}
    }