package com.shared.core.websocket.di

import com.shared.core.websocket.WebSocketHelper
import com.shared.core.websocket.WebSocketManagerImpl
import org.koin.dsl.module


val websocketModule = module {
    single<WebSocketHelper> { WebSocketManagerImpl(coroutineScope = get()) }
}

