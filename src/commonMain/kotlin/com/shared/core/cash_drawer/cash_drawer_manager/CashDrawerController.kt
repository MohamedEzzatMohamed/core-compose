package com.shared.core.cash_drawer.cash_drawer_manager

import com.shared.core.cash_drawer.cash_drawer_manager.CashDrawerManager.Companion.DrawerType

interface CashDrawerController {
    suspend fun open(
        type: DrawerType = DrawerType.WIFI,
        address: String = "",
        port: Int = 9100
    ): Result<Unit>
}