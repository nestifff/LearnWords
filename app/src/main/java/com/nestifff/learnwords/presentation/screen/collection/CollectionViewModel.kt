package com.nestifff.learnwords.presentation.screen.collection

import android.util.Log
import com.nestifff.learnwords.app.config.TAG
import com.nestifff.learnwords.app.core.StatefulViewModel
import com.nestifff.learnwords.presentation.screen.collection.model.Word
import com.nestifff.learnwords.presentation.screen.collection.utils.WordsListCreator

class CollectionViewModel(
    wordsListCreator: WordsListCreator
) : StatefulViewModel<CollectionViewModel.State, CollectionViewModel.Event>(State()) {

    data class State(
        val words: List<Word> = emptyList()
    )

    sealed class Event

    init {
        produceState(state.copy(words = wordsListCreator.getWords()))
    }

    override fun onEvent(event: Event) {

    }
}
