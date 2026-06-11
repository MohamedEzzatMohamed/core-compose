package com.shared.core.ui.bottom_indicator

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BottomLoadingIndicator(
    modifier: Modifier = Modifier,
    color: Color = Color(0xFFE7B01F)
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = 16.dp)
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
            color = color
        )
    }
}