@file:OptIn(ExperimentalForeignApi::class)

import com.shared.core.cash_drawer.cash_drawer_manager.CashDrawerController
import com.shared.core.cash_drawer.cash_drawer_manager.CashDrawerManager
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.CoreBluetooth.CBCentralManager
import platform.CoreBluetooth.CBCentralManagerDelegateProtocol
import platform.CoreBluetooth.CBCharacteristic
import platform.CoreBluetooth.CBCharacteristicPropertyWrite
import platform.CoreBluetooth.CBCharacteristicPropertyWriteWithoutResponse
import platform.CoreBluetooth.CBCharacteristicWriteWithResponse
import platform.CoreBluetooth.CBManagerStatePoweredOn
import platform.CoreBluetooth.CBPeripheral
import platform.CoreBluetooth.CBPeripheralDelegateProtocol
import platform.CoreBluetooth.CBService
import platform.CoreBluetooth.CBUUID
import platform.Foundation.NSData
import platform.Foundation.NSError
import platform.Foundation.NSNumber
import platform.Foundation.dataWithBytes
import platform.darwin.NSObject

class IosBluetoothDrawer : CashDrawerController {

    private var centralManager: CBCentralManager? = null
    private val delegate = BluetoothDelegate()

    override suspend fun open(ip: String, port: Int): Result<Unit> {
        if (centralManager == null) {
            centralManager = CBCentralManager(delegate = delegate, queue = null)
        }

        return if (delegate.isReady) {
            delegate.sendOpenCommand()
            Result.success(Unit)
        } else {
            centralManager?.let { delegate.startScanning(central = it) }
            Result.failure(Exception("Searching for and connecting to the printer..."))
        }
    }
    private class BluetoothDelegate : NSObject(), CBCentralManagerDelegateProtocol,
        CBPeripheralDelegateProtocol {

        var isReady = false
        private var targetPeripheral: CBPeripheral? = null
        private var writeCharacteristic: CBCharacteristic? = null

        private val serviceUUIDs = listOf(CBUUID.UUIDWithString("00001101-0000-1000-8000-00805F9B34FB"))

        fun startScanning(central: CBCentralManager) {
            if (central.state == CBManagerStatePoweredOn) {
                central.scanForPeripheralsWithServices(serviceUUIDs, null)
            }
        }

        fun sendOpenCommand() {
            val peripheral = targetPeripheral ?: return
            val characteristic = writeCharacteristic ?: return
            val data = CashDrawerManager.DRAWER_ADDRESS.toNSData()
            peripheral.writeValue(data, characteristic, CBCharacteristicWriteWithResponse)
        }


        override fun centralManagerDidUpdateState(central: CBCentralManager) {
            when (central.state) {
                CBManagerStatePoweredOn -> central.scanForPeripheralsWithServices(null, null)
                else -> println("Bluetooth is not available: ${central.state}")
            }
        }

        override fun centralManager(
            central: CBCentralManager,
            didDiscoverPeripheral: CBPeripheral,
            advertisementData: Map<Any?, *>,
            RSSI: NSNumber
        ) {
            targetPeripheral = didDiscoverPeripheral
            central.stopScan()
            central.connectPeripheral(didDiscoverPeripheral, null)
        }


        override fun centralManager(central: CBCentralManager, didConnectPeripheral: CBPeripheral) {
            didConnectPeripheral.delegate = this
            didConnectPeripheral.discoverServices(null)
        }

        override fun peripheral(peripheral: CBPeripheral, didDiscoverServices: NSError?) {
            peripheral.services?.forEach { service ->
                val svc = service as CBService
                peripheral.discoverCharacteristics(null, svc)
            }
        }


        override fun peripheral(
            peripheral: CBPeripheral,
            didDiscoverCharacteristicsForService: CBService,
            error: NSError?
        ) {
            didDiscoverCharacteristicsForService.characteristics?.forEach { char ->
                val characteristic = char as CBCharacteristic
                if (characteristic.properties and CBCharacteristicPropertyWrite != 0UL ||
                    characteristic.properties and CBCharacteristicPropertyWriteWithoutResponse != 0UL
                ) {
                    writeCharacteristic = characteristic
                    isReady = true
                    println("Printer is ready to receive commands")
                }
            }
        }
    }
}

fun ByteArray.toNSData(): NSData = this.usePinned { pinned ->
    NSData.dataWithBytes(pinned.addressOf(0), length = this.size.toULong())
}