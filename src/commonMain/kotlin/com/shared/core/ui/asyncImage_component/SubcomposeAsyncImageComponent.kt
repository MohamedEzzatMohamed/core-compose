package com.shared.core.ui.asyncImage_component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage


@Composable
fun SubcomposeAsyncImageComponent(
    modifier: Modifier = Modifier,
    imageUrl: Any?,
    errorPlaceholder: Painter,
    contentScale: ContentScale = ContentScale.Fit,
    circularProgressPadding: Dp = 5.dp,
    circularProgressColor: Color = MaterialTheme.colorScheme.secondary,
) {


    SubcomposeAsyncImage(
        model = imageUrl,
        contentDescription = "Image Resource",
        contentScale = contentScale,
        modifier = modifier,
        loading = {
            CircularProgressIndicator(
                color = circularProgressColor,
                modifier = modifier.padding(circularProgressPadding),
            )
        },
        error = {
            Image(
                painter = errorPlaceholder,
                contentDescription = "Loaded image",
                modifier = modifier.fillMaxSize()
            )
        }
    )
}