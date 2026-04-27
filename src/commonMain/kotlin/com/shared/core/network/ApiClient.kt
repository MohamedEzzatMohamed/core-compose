package com.shared.core.network


import com.shared.core.state.State
import io.ktor.client.HttpClient
import io.ktor.client.call.body
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
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType


class ApiClient(val client: HttpClient) {

    suspend inline fun <reified T> get(
        endpoint: String, headers: Map<String, String> = emptyMap()
    ): State<T> = execute {
        client.get(endpoint) {
            headers.forEach { (key, value) -> header(key, value) }
        }
    }


    suspend inline fun <reified T, reified R> post(
        endpoint: String, body: R, headers: Map<String, String> = emptyMap()
    ): State<T> = execute {
        client.post(endpoint) {
            headers.forEach { (key, value) -> header(key, value) }
            contentType(ContentType.Application.Json)
            setBody(body)
        }
    }


    suspend inline fun <reified T, reified R> put(
        endpoint: String, body: R, headers: Map<String, String> = emptyMap()
    ): State<T> = execute {
        client.put(endpoint) {
            headers.forEach { (key, value) -> header(key, value) }
            contentType(ContentType.Application.Json)
            setBody(body)
        }
    }


    suspend inline fun <reified T, reified R> patch(
        endpoint: String, body: R, headers: Map<String, String> = emptyMap()
    ): State<T> = execute {
        client.patch(endpoint) {
            headers.forEach { (key, value) -> header(key, value) }
            contentType(ContentType.Application.Json)
            setBody(body)
        }
    }


    suspend inline fun <reified T> delete(
        endpoint: String, headers: Map<String, String> = emptyMap()
    ): State<T> = execute {
        client.delete(endpoint) {
            headers.forEach { (key, value) -> header(key, value) }
        }
    }


    suspend fun head(endpoint: String, headers: Map<String, String> = emptyMap()): State<Unit> =
        executeNoBody {
            client.request(endpoint) {
                method = HttpMethod.Head
                headers.forEach { (key, value) -> header(key, value) }
            }
        }


    suspend fun options(endpoint: String, headers: Map<String, String> = emptyMap()): State<Unit> {
        return executeNoBody {
            client.options(endpoint) {
                headers.forEach { (key, value) -> header(key, value) }
            }
        }
    }


    suspend inline fun <reified T, reified R> request(
        method: HttpMethod,
        endpoint: String,
        body: R? = null,
        headers: Map<String, String> = emptyMap()
    ): State<T> = execute {
        client.request(endpoint) {
            this.method = method
            headers.forEach { (key, value) -> header(key, value) }

            if (body != null) {
                contentType(ContentType.Application.Json)
                setBody(body)
            }
        }
    }


    suspend inline fun <reified T> execute(block: suspend () -> HttpResponse): State<T> = try {
        val response = block()
        val code = response.status.value
        if (response.status.value in 200..299) {
            State.Success(
                data = response.body(), code = code
            )
        } else {
            State.Failure(code = code, message = response.bodyAsTextSafe())
        }

    } catch (e: Throwable) {
        State.Failure(code = null, message = e.message ?: "Unknown error", throwable = e)
    }


    suspend fun executeNoBody(block: suspend () -> HttpResponse): State<Unit> = try {
        val response = block()
        val code = response.status.value
        if (code in 200..299) {
            State.Success(Unit, code)
        } else {
            State.Failure(
                code = code, message = response.bodyAsTextSafe()
            )
        }
    } catch (e: Throwable) {
        State.Failure(code = null, message = e.message ?: "Unknown error", throwable = e)
    }


    suspend fun HttpResponse.bodyAsTextSafe() = try {
        body<String>()
    } catch (_: Exception) {
        "Unknown server error"
    }
}