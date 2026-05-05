package com.shared.core.bases.base_viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


abstract class BaseViewModel<Intent : ViewIntent, State : ViewState>(initialState: State) :
    ViewModel() {

    private val _uiStateFlow = MutableStateFlow(initialState)
    val uiStateFlow = _uiStateFlow.asStateFlow()


    private val channel = Channel<Intent>(Channel.UNLIMITED)

    init {
        processIntent()
    }

    private fun processIntent() = viewModelScope.launch {
        channel.consumeAsFlow().collect { intent -> processIntent(intent) }
    }

    abstract fun processIntent(intent: Intent)


    protected fun updateStateFlow(reducer: State.() -> State) =
        _uiStateFlow.update(reducer)


    fun sendIntent(intent: Intent) = viewModelScope.launch { channel.send(intent) }
}

interface ViewState

interface ViewIntent