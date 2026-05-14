package com.shared.core.di

import com.shared.core.cash_drawer.di.desktopCashDrawerModule
import com.shared.core.connectivity.di.jvmConnectivityManagerModule
import org.koin.dsl.module

val coreDeskTopModule = module {
    includes(desktopCashDrawerModule)
    includes(jvmConnectivityManagerModule)
}