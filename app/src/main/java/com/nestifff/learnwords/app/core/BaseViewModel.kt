package com.nestifff.learnwords.app.core

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<State : UiState, Effect : UiEffect> :
    AutoObserverViewModel() {

    private val mutableUiState: MutableStateFlow<State> by lazy {
        MutableStateFlow(createInitialState())
    }
    val uiState by lazy { mutableUiState.asStateFlow() }

    protected val state: State get() = uiState.value

    private val mutableUiEffect: MutableSharedFlow<Effect> = MutableSharedFlow()
    val uiEffect = mutableUiEffect.asSharedFlow()

    abstract fun createInitialState(): State

    fun produceState(state: State) {
        mutableUiState.value = state
    }

    fun produceEffect(effect: Effect) {
        viewModelScope.launch {
            mutableUiEffect.emit(effect)
        }
    }
}
