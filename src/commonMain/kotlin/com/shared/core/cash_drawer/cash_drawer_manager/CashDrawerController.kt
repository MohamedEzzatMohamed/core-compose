package com.shared.core.cash_drawer.cash_drawer_manager

interface CashDrawerController {
    suspend fun open(ip: String, port: Int = 9100): Result<Unit>
}