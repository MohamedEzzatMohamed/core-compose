package com.shared.core.cash_drawer.di

import com.shared.core.cash_drawer.android_bluetooth_drawer.AndroidBluetoothDrawer
import com.shared.core.cash_drawer.wifi_network_cash_drawer.WifiNetworkCashDrawer
import com.shared.core.cash_drawer.cash_drawer_manager.CashDrawerController
import com.shared.core.cash_drawer.cash_drawer_manager.CashDrawerManager
import org.koin.dsl.module


val cashDrawerModule = module {

    single<CashDrawerController> { WifiNetworkCashDrawer() }


    single<CashDrawerController> { AndroidBluetoothDrawer(context = get()) }


    single { CashDrawerManager(controllers = getAll<CashDrawerController>()) }
}