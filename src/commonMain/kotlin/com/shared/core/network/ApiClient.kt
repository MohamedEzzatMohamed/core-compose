package com.shared.core.network

import com.shared.core.state.State
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.authProviders
import io.ktor.client.plugins.auth.providers.BearerAuthProvider
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.options
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType

class ApiClient(val client: HttpClient) {


    suspend inline fun <reified T> get(
        endpoint: String,
        headers: Map<String, String> = emptyMap()
    ): NetworkResult<T> = execute {
        client.get(urlString = endpoint) {
            headers.forEach { (key, value) -> header(key, value) }
        }
    }

    suspend inline fun <reified T, reified R> post(
        endpoint: String, body: R,
        headers: Map<String, String> = emptyMap()
    ): NetworkResult<T> = execute {
        client.post(urlString = endpoint) {
            headers.forEach { (key, value) -> header(key, value) }
            setBody(body)
        }
    }


    suspend inline fun <reified T, reified R> put(
        endpoint: String, body: R,
        headers: Map<String, String> = emptyMap()
    ): NetworkResult<T> = execute {
        client.put(endpoint) {
            headers.forEach { (key, value) -> header(key, value) }
            setBody(body)
        }
    }



    suspend inline fun <reified T, reified R> patch(
        endpoint: String, body: R,
        headers: Map<String, String> = emptyMap()
    ): NetworkResult<T> = execute {
        client.patch(urlString = endpoint) {
            headers.forEach { (key, value) -> header(key, value) }
            setBody(body)
        }
    }


    suspend inline fun <reified T, reified R> delete(
        endpoint: String, body: R,
        headers: Map<String, String> = emptyMap()
    ): NetworkResult<T> = execute {
        client.delete(urlString = endpoint) {
            headers.forEach { (key, value) -> header(key, value) }
            setBody(body)
        }
    }


    suspend inline fun <reified T, reified R> request(
        method: HttpMethod,
        endpoint: String,
        body: R? = null,
        headers: Map<String, String> = emptyMap()
    ): NetworkResult<T> = execute {
        client.request(urlString = endpoint) {
            this.method = method
            headers.forEach { (key, value) -> header(key, value) }
            if (body != null) {
                contentType(ContentType.Application.Json)
                setBody(body)
            }
        }
    }


    suspend fun head(
        endpoint: String,
        headers: Map<String, String> = emptyMap()
    ): State<Unit> = executeNoBody {
        client.request(urlString = endpoint) {
            method = HttpMethod.Head
            headers.forEach { (key, value) -> header(key, value) }
        }
    }


    suspend fun options(
        endpoint: String,
        headers: Map<String, String> = emptyMap()
    ): State<Unit> = executeNoBody {
        client.options(urlString = endpoint) {
            headers.forEach { (key, value) -> header(key, value) }
        }
    }


    suspend fun clearCashedToken(onClearCash : suspend () -> Unit) {
        onClearCash()
        client.authProviders.filterIsInstance<BearerAuthProvider>().forEach { it.clearToken() }
    }

    suspend inline fun <reified T> execute(
        block: suspend () -> HttpResponse
    ): NetworkResult<T> = try {
        val response = block()
        val code = response.status.value
        if (code in 200..299) {
            NetworkResult.Success(data = response.body<T>(), code = code)
        } else {
            NetworkResult.Failure(code = code, message = response.bodyAsText())
        }
    } catch (e: Exception) {
        NetworkResult.Failure(
            code = null, message = e.message ?: "Unknown error", throwable = e
        )
    }


    suspend fun executeNoBody(
        block: suspend () -> HttpResponse
    ): State<Unit> = try {
        val response = block()
        val code = response.status.value
        if (code in 200..299) {
            State.Success(Unit, code)
        } else {
            State.Failure(code = code, message = response.bodyAsText())
        }
    } catch (e: Exception) {
        State.Failure(code = null, message = e.message ?: "Unknown error", throwable = e)
    }
}