package com.shared.core.ui.bottom_dialog

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.union
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetComponent(
    showBottomSheet: Boolean,
    containerColor: Color = Color(0xFFFFFFFF),
    hideBottomSheetVisible: () -> Unit,
    content: @Composable () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (sheetState.isVisible.not()) hideBottomSheetVisible()
                }
            },
            sheetState = sheetState,
            containerColor = containerColor,
            content = { content() },
            contentWindowInsets = { BottomSheetDefaults.windowInsets.union(WindowInsets.ime) }
        )
    }
}