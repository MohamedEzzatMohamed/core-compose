package com.shared.core.ui.dialog_component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties


@Composable
fun DialogComponent(
    modifier: Modifier = Modifier,
    showDialog: Boolean,
    shape: RoundedCornerShape = RoundedCornerShape(12.dp),
    backgroundColor: Color = Color.White,
    contentColor: Color = Color.Black,
    content: @Composable () -> Unit
) {
    if (showDialog) {
        Dialog(
            onDismissRequest = {},
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
                usePlatformDefaultWidth = false
            ),
        ) {
            Surface(
                modifier = modifier,
                shape = shape,
                color = backgroundColor,
                contentColor = contentColor,
                tonalElevation = AlertDialogDefaults.TonalElevation,
            ) {
                content()
            }
        }
    }
}