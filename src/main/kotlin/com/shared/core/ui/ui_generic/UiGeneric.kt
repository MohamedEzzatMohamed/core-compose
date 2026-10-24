package com.shared.core.ui.ui_generic

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun <T> GeneralLazyColumn(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(16.dp),
    contentPadding: PaddingValues = PaddingValues(vertical = 16.dp),
    list: List<T>, content: @Composable (T) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = verticalArrangement,
        contentPadding = contentPadding,
    ) {
        items(items = list) { item ->
            content(item)
        }
    }
}