package com.shared.core.ui.custom_surface

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun CustomSurface(
    modifier: Modifier = Modifier,
    borderWidth: Dp = 1.dp,
    borderColor: Color = Color(0xFFF4F4F5),
    shape: Shape = RoundedCornerShape(12.dp),
    surfaceBackground: Color = Color(0xFFFFFFFF),
    padding: Dp = 8.dp,
    content: @Composable () -> Unit,
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = borderWidth, color = borderColor,
                shape = shape
            )
            .background(color = surfaceBackground)
            .padding(padding)
    ) {
        content()
    }
}