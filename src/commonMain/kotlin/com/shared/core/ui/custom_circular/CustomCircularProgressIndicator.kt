package com.shared.core.ui.custom_circular

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


@Composable
fun CustomCircularProgressIndicator(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    background: Color = Color(0xFFFFFFFF),
    color: Color = Color(0xFFFEBF00),
    trackColor: Color = Color(0xFFFEBF00)
) {
    Box(
        modifier.fillMaxSize().background(color = background),
        contentAlignment = contentAlignment
    ) {
        CircularProgressIndicator(
            color = color,
            trackColor = trackColor,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}