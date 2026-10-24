package com.shared.core.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object TokenRefreshService {

    val refreshClient = HttpClient(engineFactory = platformHttpEngine()) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    suspend inline fun <reified T> performTokenRefresh(fullUrl: String, body: Any): T {
        return refreshClient.post(fullUrl) {
            contentType(ContentType.Application.Json)
            setBody(body)
        }.body()
    }
}