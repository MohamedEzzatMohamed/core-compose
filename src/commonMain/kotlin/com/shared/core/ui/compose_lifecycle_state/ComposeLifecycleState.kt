package com.shared.core.ui.compose_lifecycle_state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle


@Composable
fun ComposeLifecycleState(
    state: Lifecycle.State = Lifecycle.State.STARTED,
    execution: suspend () -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(state) { execution() }
    }
}