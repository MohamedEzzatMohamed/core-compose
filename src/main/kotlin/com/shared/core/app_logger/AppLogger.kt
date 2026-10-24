package com.shared.core.app_logger

import android.util.Log

object AppLogger {
    fun d(message: String, tag: String = "DEBUG") {
        Log.d(tag, message)
    }

    fun i(message: String, tag: String = "INFO") {
        Log.i(tag, message)
    }

    fun e(message: String, tag: String = "ERROR", throwable: Throwable? = null) {
        Log.e(tag, message, throwable)
    }
}
