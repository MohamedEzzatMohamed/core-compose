package com.shared.core.ui.input_fields

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController


@Composable
fun Modifier.clearFocusOnTouch(): Modifier {
    val localFocusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    return this then Modifier.pointerInput(Unit) {
        detectTapGestures {
            keyboardController?.hide()
            localFocusManager.clearFocus(force = true)
        }
    }
}