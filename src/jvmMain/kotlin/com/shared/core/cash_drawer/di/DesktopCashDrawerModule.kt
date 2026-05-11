package com.shared.core.cash_drawer.di

import com.shared.core.cash_drawer.cash_drawer_manager.CashDrawerController
import com.shared.core.cash_drawer.cash_drawer_manager.CashDrawerManager
import com.shared.core.cash_drawer.cash_drawer_manager.CashDrawerManager.Companion.DrawerType
import com.shared.core.cash_drawer.desktop_serial_drawer.DesktopSerialDrawer
import com.shared.core.cash_drawer.wifi_network_cash_drawer.WifiNetworkCashDrawer
import org.koin.core.qualifier.named
import org.koin.dsl.module


val desktopCashDrawerModule = module {

    single<CashDrawerController>(qualifier = named(enum = DrawerType.WIFI)) {
        WifiNetworkCashDrawer()
    }

    single<CashDrawerController>(qualifier = named(enum = DrawerType.USB)) {
        DesktopSerialDrawer()
    }

    single {
        CashDrawerManager(
            controllers = listOf(
                get(qualifier = named(enum = DrawerType.WIFI)),
                get(qualifier = named(enum = DrawerType.USB)),
            )
        )
    }
}