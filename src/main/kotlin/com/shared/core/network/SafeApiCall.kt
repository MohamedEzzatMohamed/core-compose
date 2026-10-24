package com.shared.core.network

import com.shared.core.app_error.AppError
import com.shared.core.state.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.cancellation.CancellationException


inline fun <DTO, DOMAIN> safeApiCall(
    crossinline apiCall: suspend () -> NetworkResult<DTO>,
    crossinline mapper: suspend (DTO) -> DOMAIN
): Flow<State<DOMAIN>> = flow {

    emit(value = State.Loading())

    when (val result = apiCall()) {

        is NetworkResult.Success -> emit(
            value = State.Success(data = mapper(result.data), code = result.code)
        )

        is NetworkResult.Failure -> {
            emit(value = State.Error(error = mapNetworkError(result)))
        }
    }
}.catch { e ->
    if (e is CancellationException) throw e
    emit(State.Error(error = AppError.Unknown(e.message ?: "Unknown error")))
}.flowOn(Dispatchers.IO)


fun mapNetworkError(result: NetworkResult.Failure): AppError {
    return when (result.code) {
        null -> AppError.NoInternet
        401 -> AppError.Unauthorized
        403 -> AppError.Forbidden
        404 -> AppError.NotFound(result.message)
        in 500..599 -> AppError.ServerError
        else -> AppError.Unknown(result.message)
    }
}


