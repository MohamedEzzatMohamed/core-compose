package com.shared.core.connectivity

import com.shared.core.app_logger.AppLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.isActive
import java.net.InetSocketAddress
import java.net.Socket

class JvmConnectivityManager(
    coroutineScope: CoroutineScope
) : ConnectivityHelper {

    override val isConnected: StateFlow<Boolean> =
        flow {
            while (currentCoroutineContext().isActive) {

                emit(isConnectedNow())

                delay(5_000)
            }
        }.distinctUntilChanged()
            .stateIn(
                scope = coroutineScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = isConnectedNow()
            )

    private fun isConnectedNow(): Boolean {
        return try {
            Socket().use { socket ->
                socket.connect(InetSocketAddress("8.8.8.8", 53), 2_000)
            }
            true
        } catch (e: Exception) {
            AppLogger.e(message = "JVM Cannot be connect with internet: $e")
            false
        }
    }
}