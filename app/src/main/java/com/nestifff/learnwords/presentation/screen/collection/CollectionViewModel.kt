package com.nestifff.learnwords.presentation.screen.collection

import com.nestifff.learnwords.app.core.StatefulViewModel

class CollectionViewModel :
    StatefulViewModel<CollectionViewModel.State, CollectionViewModel.Event>(State()) {

    data class State(
        val words: List<Any> = emptyList()
    )

    sealed class Event {

    }

    override fun onEvent(event: Event) {

    }
}
