package com.shared.core.cash_drawer.di

import com.shared.core.cash_drawer.desktop_serial_drawer.DesktopSerialDrawer
import com.shared.core.cash_drawer.cash_drawer_manager.CashDrawerController
import com.shared.core.cash_drawer.cash_drawer_manager.CashDrawerManager
import com.shared.core.cash_drawer.wifi_network_cash_drawer.WifiNetworkCashDrawer
import org.koin.dsl.module


val desktopCashDrawerModule = module {

    single { CashDrawerManager(controllers = getAll<CashDrawerController>()) }

    single<CashDrawerController> { WifiNetworkCashDrawer() }

    single<CashDrawerController> { DesktopSerialDrawer() }
}