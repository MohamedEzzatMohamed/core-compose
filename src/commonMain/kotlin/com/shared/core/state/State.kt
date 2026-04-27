package com.shared.core.state

import com.shared.core.error.AppError


sealed class State<out T> {

    class Loading<T> : State<T>()
    data class Success<T>(val data: T? = null, val code: Int, val error: AppError? = null) :
        State<T>()

    data class Error<T>(val error: AppError) : State<T>()

    data class Failure(val code: Int?, val message: String, val throwable: Throwable? = null) :
        State<Nothing>()
}