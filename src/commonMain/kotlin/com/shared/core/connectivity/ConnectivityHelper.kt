package com.shared.core.connectivity

import kotlinx.coroutines.flow.StateFlow

interface ConnectivityHelper {

    val isConnected: StateFlow<Boolean>
}