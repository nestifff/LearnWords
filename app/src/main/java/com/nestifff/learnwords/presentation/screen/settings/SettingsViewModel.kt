package com.nestifff.learnwords.presentation.screen.settings

import android.util.Log
import com.nestifff.learnwords.app.config.TAG
import com.nestifff.learnwords.app.core.StatefulViewModel

class SettingsViewModel :
    StatefulViewModel<SettingsViewModel.State, SettingsViewModel.Event>(State()) {

    data class State(
        val words: List<Any> = emptyList()
    )

    sealed class Event {

    }

    override fun onEvent(event: Event) {

    }
}
