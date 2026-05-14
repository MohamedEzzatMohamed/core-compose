package com.shared.core.connectivity.di

import com.shared.core.connectivity.ConnectivityHelper
import com.shared.core.connectivity.JvmConnectivityManager
import org.koin.dsl.module


val jvmConnectivityManagerModule = module {
    single<ConnectivityHelper> { JvmConnectivityManager(coroutineScope = get()) }
}