package com.shared.core.websocket

import com.shared.core.websocket.provide_http_client.provideHttpClient
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.readBytes
import io.ktor.websocket.readText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


class WebSocketManagerImpl(private val coroutineScope: CoroutineScope) : WebSocketHelper {

    override var onMessageReceived: ((String) -> Unit)? = null

    private val client = provideHttpClient()

    private var session: WebSocketSession? = null


    override fun connect() {
        coroutineScope.launch {
            try {
                client.webSocket(urlString = "wss://echo.websocket.org") {
                    session = this
                    println("WebSocket opened")
                    for (frame in incoming) {
                        setIncomingFrame(frame = frame)
                    }
                }
            } catch (e: Exception) {
                println("WebSocket connect error: ${e.message}")
            }
        }
    }

    private fun setIncomingFrame(frame: Frame) {
        when (frame) {
            is Frame.Text -> {
                val text = frame.readText()
                onMessageReceived?.invoke(text)
            }

            is Frame.Binary -> {
                val bytes = frame.readBytes()
                val text = bytes.decodeToString()
                onMessageReceived?.invoke(text)
            }

            else -> null
        }
    }

    override fun sendMessage(message: String) {
        coroutineScope.launch { session?.send(Frame.Text(message)) }
    }

    override fun close() {
        coroutineScope.launch { session?.close() }
        println("WebSocket closed")
    }
}
