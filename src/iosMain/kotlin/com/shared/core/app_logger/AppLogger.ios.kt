package com.shared.core.app_logger

import platform.Foundation.NSLog

actual object AppLogger {

    actual fun d(message: String, tag: String) {
        NSLog("DEBUG: [$tag] $message")
    }

    actual fun i(message: String, tag: String) {
        NSLog("INFO: [$tag] $message")
    }

    actual fun e(message: String, tag: String, throwable: Throwable?) {
        val errorDetail = throwable?.let { "\nError: ${it.message}" } ?: ""
        NSLog("ERROR: [$tag] $message $errorDetail")
    }
}