package com.shared.core.app_logger

import android.util.Log

actual object AppLogger {

    actual fun d(message: String, tag: String) {
        Log.d(tag, message)
    }

    actual fun i(message: String, tag: String) {
        Log.i(tag, message)
    }

    actual fun e(message: String, tag: String, throwable: Throwable?) {
        Log.e(tag, message, throwable)
    }
}