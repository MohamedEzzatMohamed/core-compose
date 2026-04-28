package com.shared.core.error

import com.shared.core.app_logger.AppLogger

sealed class AppError {
    var exception: Throwable? = GeneralException()
    var extraData: Any? = null
    var message: String? = null
    var logMessage: String? = null
    var logTag: String = "Platform Error =>"
    var logPriority: ErrorLogPriority = ErrorLogPriority.ERROR

    fun logError() {

        val message =
            "$logTag \nMessage: $message \nLog Message: $logMessage\nExtra Data: $extraData\n"

        when (logPriority) {

            ErrorLogPriority.ERROR -> AppLogger.e(tag = logTag, message = message, throwable = exception)

            ErrorLogPriority.INFO -> AppLogger.i(tag = logTag, message = message)

            ErrorLogPriority.WARN -> AppLogger.e(tag = logTag, message = message)
        }
    }


    class E(
        exception: Exception,
        message: String? = null,
        logMessage: String? = null,
        logTag: String? = null,
        extraData: Any? = null,
    ) : AppError() {
        init {
            this.logPriority = ErrorLogPriority.ERROR
            this.message = message
            this.logMessage = logMessage
            logTag?.let { this.logTag = it }
            this.exception = exception
            this.extraData = extraData
        }

    }

    class W(
        exception: Exception,
        message: String? = null,
        logMessage: String? = null,
        logTag: String? = null,
        extraData: Any? = null,
    ) : AppError() {
        init {
            this.logPriority = ErrorLogPriority.WARN
            this.message = message
            this.logMessage = logMessage
            logTag?.let { this.logTag = it }
            this.exception = exception
            this.extraData = extraData
        }

    }

    class I(
        exception: Exception,
        message: String? = null,
        logMessage: String? = null,
        logTag: String? = null,
        extraData: Any? = null,
    ) : AppError() {
        init {
            this.logPriority = ErrorLogPriority.INFO
            this.message = message
            this.logMessage = logMessage
            logTag?.let { this.logTag = it }
            this.exception = exception
            this.extraData = extraData
        }

    }
}