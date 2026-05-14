package com.shared.core.connectivity.di

import com.shared.core.connectivity.ConnectivityHelper
import com.shared.core.connectivity.IOSConnectivityManager
import org.koin.dsl.module


val iOSConnectivityManagerModule = module {
    single<ConnectivityHelper> { IOSConnectivityManager(coroutineScope = get()) }
}