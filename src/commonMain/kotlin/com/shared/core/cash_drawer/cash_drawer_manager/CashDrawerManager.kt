package com.shared.core.cash_drawer.cash_drawer_manager


class CashDrawerManager(
    private val controllers: List<CashDrawerController>
) {
    suspend fun open(ip: String): Result<Unit> {
        if (controllers.isEmpty()) return Result.failure(Exception("No controllers available"))
        controllers.forEach { controller ->
            val result = controller.open(ip)
            if (result.isSuccess) return result
        }
        return Result.failure(Exception("Could not open drawer via any available method"))
    }

    companion object{
        val DRAWER_ADDRESS = byteArrayOf(
            0x1B, 0x70, 0x00, 0x19, 0xFA.toByte()
        )
    }
}