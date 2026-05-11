package com.shared.core.cash_drawer.wifi_network_cash_drawer

import com.shared.core.cash_drawer.cash_drawer_manager.CashDrawerController
import com.shared.core.cash_drawer.cash_drawer_manager.CashDrawerManager.Companion.DRAWER_ADDRESS
import com.shared.core.cash_drawer.cash_drawer_manager.CashDrawerManager.Companion.DrawerType
import io.ktor.network.selector.SelectorManager
import io.ktor.network.sockets.aSocket
import io.ktor.network.sockets.openWriteChannel
import io.ktor.util.cio.use
import io.ktor.utils.io.writeFully
import kotlinx.coroutines.Dispatchers

class WifiNetworkCashDrawer : CashDrawerController {
    override suspend fun open(type: DrawerType, address: String, port: Int): Result<Unit> =
        runCatching {
            val selectorManager = SelectorManager(Dispatchers.Default)
            val socket = aSocket(selectorManager).tcp().connect(hostname = address, port)
            socket.openWriteChannel(autoFlush = true).use {
                writeFully(value = DRAWER_ADDRESS)
            }
            socket.close()
        }
}