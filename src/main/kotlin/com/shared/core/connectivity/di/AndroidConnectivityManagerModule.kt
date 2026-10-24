package com.shared.core.connectivity.di

import com.shared.core.connectivity.ConnectivityHelper
import com.shared.core.connectivity.connectivity_manager.AndroidConnectivityManager
import com.shared.core.connectivity.internet_access_observer.InternetAccessObserver
import org.koin.dsl.module


val androidConnectivityManagerModule = module {

    single<ConnectivityHelper> {
        val internetAccessObserver = InternetAccessObserver(applicationScope = get())
        AndroidConnectivityManager(
            context = get(),
            internetAccessObserver = internetAccessObserver
        )
    }
}