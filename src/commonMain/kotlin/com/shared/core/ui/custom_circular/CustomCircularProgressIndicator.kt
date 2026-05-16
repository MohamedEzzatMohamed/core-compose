package com.shared.core.ui.custom_circular

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun CustomCircularProgressIndicator(
    modifier: Modifier = Modifier,
    background: Color = Color.Black.copy(alpha = 0.3f),
    borderRadius: Dp = 16.dp,
    color: Color = Color(0xFFFEBF00),
    trackColor: Color = ProgressIndicatorDefaults.circularIndeterminateTrackColor
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = background),
        contentAlignment = Alignment.Center
    ) {

        Box(
            modifier = Modifier
                .size(100.dp)
                .background(
                    color = background,
                    shape = RoundedCornerShape(size = borderRadius)
                ),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = color,
                trackColor = trackColor,
                modifier = Modifier.size(40.dp)
            )
        }
    }
}