package com.shared.core.network

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.okhttp.OkHttp

fun platformHttpEngine(): HttpClientEngineFactory<*> {
    return OkHttp
}
