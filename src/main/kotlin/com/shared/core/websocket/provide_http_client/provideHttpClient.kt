package com.shared.core.websocket.provide_http_client

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.websocket.WebSockets

fun provideHttpClient(): HttpClient = HttpClient(OkHttp) {
    install(WebSockets)
}
