package com.shared.core.cash_drawer.cash_drawer_manager

import com.shared.core.cash_drawer.wifi_network_cash_drawer.WifiNetworkCashDrawer


class CashDrawerManager(
    private val controllers: List<CashDrawerController>
) {
    suspend fun open(type: DrawerType, address: String, port: Int): Result<Unit> {
        val controller = when (type) {

            DrawerType.WIFI -> controllers.filterIsInstance<WifiNetworkCashDrawer>().firstOrNull()

            DrawerType.BLUETOOTH -> controllers.firstOrNull {
                it::class.simpleName?.contains("Bluetooth", ignoreCase = true) == true
            }

            DrawerType.USB -> controllers.firstOrNull {
                it::class.simpleName?.contains("Usb", ignoreCase = true) == true ||
                        it::class.simpleName?.contains("Serial", ignoreCase = true) == true

            }
        }

        return controller?.open(address = address, port = port)
            ?: Result.failure(Exception("The required controller is not available on this device."))
    }

    companion object {
        enum class DrawerType { WIFI, BLUETOOTH, USB }

        val DRAWER_ADDRESS = byteArrayOf(
            0x1B, 0x70, 0x00, 0x19, 0xFA.toByte()
        )

    }
}