package com.nestifff.learnwords.app.core

import kotlinx.coroutines.flow.MutableStateFlow

abstract class StatefulViewModel<State : Any, Event : Any>(private val defaultState: State) :
    AutoObserverViewModel(), EventHandler<Event> {

    val stateData: MutableStateFlow<State> = MutableStateFlow(defaultState)

    private var _state: State = defaultState

    val state: State get() = _state

    fun produceState(state: State) {
        this._state = state
        stateData.value = state
    }

    fun getDefaultState() = defaultState
}
