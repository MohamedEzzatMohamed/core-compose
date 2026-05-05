package com.shared.core.app_error

sealed interface AppError {
    data object NoInternet : AppError
    data object Unauthorized : AppError
    data object Forbidden : AppError
    data object NotFound : AppError
    data object ServerError : AppError
    data class Unknown(val message: String) : AppError
}