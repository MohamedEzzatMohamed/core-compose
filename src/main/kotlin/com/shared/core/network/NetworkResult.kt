package com.shared.core.network

sealed class NetworkResult<out T> {
    data class Success<T>(val data: T, val code: Int) : NetworkResult<T>()

    data class Failure(val code: Int?, val message: String, val throwable: Throwable? = null) :
        NetworkResult<Nothing>()
}