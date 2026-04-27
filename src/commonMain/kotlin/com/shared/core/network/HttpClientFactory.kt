package com.shared.core.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClientFactory {
    fun create(baseUrl: String, tokenProvider: () -> String? = { null }) =

        HttpClient(engineFactory = platformHttpEngine()) {

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

            val loggingPlugin = createClientPlugin("LoggingPlugin") {
                onRequest { request, _ ->
                    println("Sending request: ${request.url}")
                }
                onResponse { response ->
                    println("Received response: ${response.bodyAsText()}")
                }
            }

            install(plugin = loggingPlugin)

            install(plugin = Logging) {
                logger = Logger.DEFAULT
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

                tokenProvider()?.let {
                    header(HttpHeaders.Authorization, "Bearer $it")
                }
            }
        }
}
