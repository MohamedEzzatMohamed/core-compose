package com.shared.core.cash_drawer.desktop_serial_drawer

import com.fazecast.jSerialComm.SerialPort
import com.shared.core.cash_drawer.cash_drawer_manager.CashDrawerController
import com.shared.core.cash_drawer.cash_drawer_manager.CashDrawerManager

class DesktopSerialDrawer : CashDrawerController {

    override suspend fun open(ip: String, port: Int): Result<Unit> = runCatching {
        val commPort = SerialPort.getCommPorts().firstOrNull {
            it.descriptivePortName.contains("Printer", ignoreCase = true) ||
                    it.systemPortName == ip
        } ?: throw Exception("No printer was found connected via USB or Bluetooth.")

        commPort.baudRate = 9600
        commPort.numDataBits = 8
        commPort.numStopBits = SerialPort.ONE_STOP_BIT
        commPort.parity = SerialPort.NO_PARITY

        if (commPort.openPort()) {
            val openCommand = CashDrawerManager.DRAWER_ADDRESS
            commPort.writeBytes(openCommand, openCommand.size)
            Thread.sleep(100)
            commPort.closePort()
        } else {
            throw Exception("The port failed to open; it may be in use by another program.")
        }
    }
}