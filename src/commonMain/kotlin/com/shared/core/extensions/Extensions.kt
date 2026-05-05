package com.shared.core.extensions


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shared.core.app_error.AppError
import com.shared.core.state.State
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json


fun ViewModel.viewModelScope(
    context: CoroutineDispatcher = Dispatchers.IO,
    block: suspend () -> Unit
) = this.viewModelScope.launch(context = context) {
    block()
}


fun <T> KSerializer<T>.encodeToString(value: T) =
    Json.encodeToString(serializer = this, value = value)

fun <T> KSerializer<T>.decodeFromString(jsonString: String) =
    Json.decodeFromString(deserializer = this, string = jsonString)


suspend inline fun <T> Flow<State<T>>.collectOnFlowState(
    crossinline onLoading: () -> Unit = {},
    crossinline onFailure: (code: Int?, message: String, throwable: Throwable?) -> Unit = { _, _, _ -> },
    crossinline onError: (AppError) -> Unit,
    crossinline onSuccess: suspend (T) -> Unit,
) = collect {
    onLoading()
    when (it) {
        is State.Loading -> {}
        is State.Failure -> onFailure(it.code, it.message, it.throwable)
        is State.Error -> onError(it.error)
        is State.Success -> onSuccess(it.data ?: return@collect)
    }
}