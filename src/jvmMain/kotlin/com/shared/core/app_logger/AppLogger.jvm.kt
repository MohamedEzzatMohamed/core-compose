package com.shared.core.app_logger

actual object AppLogger {
    actual fun e(
        priority: Int,
        tag: String,
        message: String,
        throwable: Throwable?
    ) {
    }

    actual fun w(priority: Int, tag: String, message: String) {
    }

    actual fun i(priority: Int, tag: String, message: String) {
    }
}