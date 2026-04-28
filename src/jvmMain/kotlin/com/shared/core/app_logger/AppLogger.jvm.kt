package com.shared.core.app_logger

actual object AppLogger {
    actual fun d(message: String, tag: String) {
        println("DEBUG: [$tag] $message")
    }

    actual fun i(message: String, tag: String) {
        println("INFO: [$tag] $message")
    }

    actual fun e(message: String, tag: String, throwable: Throwable?) {
        System.err.println("ERROR: [$tag] $message")
        throwable?.printStackTrace()
    }
}