package com.shared.core.di

import com.shared.core.cash_drawer.di.cashDrawerModule
import com.shared.core.connectivity.di.androidConnectivityManagerModule
import org.koin.dsl.module

val coreAndroidModule = module {
    includes(cashDrawerModule)
    includes(androidConnectivityManagerModule)
}