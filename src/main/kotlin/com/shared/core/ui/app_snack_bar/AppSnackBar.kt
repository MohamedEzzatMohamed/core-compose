package com.shared.core.ui.app_snack_bar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay


@Composable
fun AppSnackBar(
    modifier: Modifier = Modifier,
    showSnackBar: Boolean,
    duration: Long = 2000,
    align: Alignment = Alignment.BottomCenter,
    containerColor: Color = SnackbarDefaults.color,
    shape: Shape = RoundedCornerShape(size = 8.dp),
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {
    if (!showSnackBar) return
    LaunchedEffect(showSnackBar) {
        delay(duration)
        onDismiss()
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Snackbar(
            modifier = modifier.align(alignment = align).clip(shape),
            containerColor = containerColor
        ) {
            content()
        }
    }
}

