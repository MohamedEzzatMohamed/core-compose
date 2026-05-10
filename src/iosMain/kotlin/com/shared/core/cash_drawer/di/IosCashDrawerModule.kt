package com.shared.core.cash_drawer.di

import IosBluetoothDrawer
import com.shared.core.cash_drawer.wifi_network_cash_drawer.WifiNetworkCashDrawer
import com.shared.core.cash_drawer.cash_drawer_manager.CashDrawerController
import com.shared.core.cash_drawer.cash_drawer_manager.CashDrawerManager
import org.koin.dsl.module


val iosCashDrawerModule = module {
    single { CashDrawerManager(controllers = getAll<CashDrawerController>()) }
    single<CashDrawerController> { WifiNetworkCashDrawer() }
    single<CashDrawerController> { IosBluetoothDrawer() }
}