package com.nestifff.learnwords.presentation.screen.settings

import com.nestifff.learnwords.app.core.BaseViewModel
import com.nestifff.learnwords.app.core.UiEffect
import com.nestifff.learnwords.app.core.UiEvent
import com.nestifff.learnwords.app.core.UiState

class SettingsViewModel :
    BaseViewModel<SettingsViewModel.State, SettingsViewModel.Event, SettingsViewModel.Effect>() {

    data class State(
        val words: List<Any> = emptyList()
    ) : UiState

    sealed class Event : UiEvent

    sealed class Effect : UiEffect

    override fun createInitialState(): State = State()

    override fun onEvent(event: Event) {

    }
}
