package com.shared.core.ui.icon_wrapper

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun IconWrapper(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    color: Color = Color.Transparent,
    size: Dp = 24.dp,
    border: BorderStroke? = null,
    iconContent: @Composable () -> Unit
) {
    Surface(modifier = modifier, shape = shape, color = color, border = border) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(size)
        ) { iconContent() }
    }
}
