package com.shared.core.app_logger

expect object AppLogger {
    fun d(message: String, tag: String = "DEBUG")
    fun i(message: String, tag: String = "INFO")
    fun e(message: String, tag: String = "ERROR", throwable: Throwable? = null)

}