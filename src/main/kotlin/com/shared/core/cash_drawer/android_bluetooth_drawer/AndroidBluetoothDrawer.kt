package com.shared.core.cash_drawer.android_bluetooth_drawer

import android.Manifest
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import com.shared.core.cash_drawer.cash_drawer_manager.CashDrawerController
import com.shared.core.cash_drawer.cash_drawer_manager.CashDrawerManager
import com.shared.core.cash_drawer.cash_drawer_manager.CashDrawerManager.Companion.DrawerType
import java.util.UUID

class AndroidBluetoothDrawer(private val context: Context) : CashDrawerController {

    override suspend fun open(type: DrawerType, address: String, port: Int): Result<Unit> = runCatching {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S &&
            context.checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            throw Exception("Missing Bluetooth Permission")
        }

        val adapter = context.getSystemService(BluetoothManager::class.java).adapter
        val device = adapter.bondedDevices.firstOrNull() ?: throw Exception("No paired device")

        val socket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"))
        socket.connect()
        socket.outputStream.write(CashDrawerManager.DRAWER_ADDRESS)
        socket.close()
    }
}