package com.shared.core.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.RefreshTokensParams
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import ro.cosminmihu.ktor.monitor.ContentLength
import ro.cosminmihu.ktor.monitor.KtorMonitorLogging
import ro.cosminmihu.ktor.monitor.RetentionPeriod

object HttpClientFactory {
    fun create(
        baseUrl: String,
        bearerTokensBlock: suspend () -> BearerTokens? = { null },
        refreshTokensBlock: suspend RefreshTokensParams.() -> BearerTokens? = { null },
        httpRequestBuilderBlock: (HttpRequestBuilder) -> Boolean = { true }
    ) = HttpClient(engineFactory = platformHttpEngine()) {

        expectSuccess = false

        install(plugin = ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    explicitNulls = false
                    isLenient = true
                    coerceInputValues = true
                })
        }

        install(plugin = Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    println("HTTP Client: $message")
                }
            }
            level = LogLevel.BODY
        }

        install(plugin = HttpTimeout) {
            requestTimeoutMillis = 30_000
            connectTimeoutMillis = 30_000
            socketTimeoutMillis = 30_000
        }

        install(plugin = DefaultRequest) {
            url(urlString = baseUrl)
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }

        install(plugin = Auth) {
            bearer {
                loadTokens { bearerTokensBlock() }
                refreshTokens { refreshTokensBlock() }
                sendWithoutRequest { request -> httpRequestBuilderBlock(request) }
            }
        }

        install(plugin = KtorMonitorLogging) {
            sanitizeHeader { header -> header == "Authorization" }
            filter { request -> !request.url.host.contains("cosminmihu.ro") }
            showNotification = true
            retentionPeriod = RetentionPeriod.OneHour
            maxContentLength = ContentLength.Default
        }
    }
}
