package com.shared.core.di

import com.shared.core.coroutines_scope.CoroutinesScopeModule
import com.shared.core.data_store_module.dataStorePlatformModule
import com.shared.core.locale_manager.di.localeManagerModule
import com.shared.core.network.di.networkModule
import com.shared.core.websocket.di.websocketModule
import org.koin.dsl.module


val coreModule = module {
    includes(
        websocketModule,
        CoroutinesScopeModule,
        dataStorePlatformModule,
        localeManagerModule,
        networkModule,
    )
}