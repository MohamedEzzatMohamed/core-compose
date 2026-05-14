package com.shared.core.ui.connectivity_banner_state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.shared.core.ui.connectivity_banner_state.enums.ConnectionBannerState
import kotlinx.coroutines.delay

@Composable
fun rememberConnectionBannerState(isConnected: Boolean): State<ConnectionBannerState> {

    val bannerState = remember { mutableStateOf(value = ConnectionBannerState.Hidden) }

    var wasDisconnected by remember { mutableStateOf(value = false) }

    LaunchedEffect(key1 = isConnected) {
        if (isConnected) {
            if (wasDisconnected) {
                bannerState.value = ConnectionBannerState.Connected
                delay(timeMillis = 2000)
                bannerState.value = ConnectionBannerState.Hidden
                wasDisconnected = false
            }
        } else {
            wasDisconnected = true
            bannerState.value = ConnectionBannerState.Disconnected
        }
    }
    return bannerState
}