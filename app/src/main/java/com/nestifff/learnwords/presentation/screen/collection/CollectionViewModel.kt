package com.nestifff.learnwords.presentation.screen.collection

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.nestifff.learnwords.app.config.TAG
import com.nestifff.learnwords.app.core.StatefulViewModel
import com.nestifff.learnwords.ext.generateUUID
import com.nestifff.learnwords.presentation.screen.collection.mapper.toWordCollectionScreen
import com.nestifff.learnwords.presentation.screen.collection.model.WordCollectionScreen
import com.nestifff.words.domain.model.WordDomain
import com.nestifff.words.domain.usecase.AddWordUseCase
import com.nestifff.words.domain.usecase.GetAllWordsFlowUseCase
import com.nestifff.words.domain.usecase.GetAllWordsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CollectionViewModel(
    private val getAllWordsFlowUseCase: GetAllWordsFlowUseCase,
    private val getAllWordsUseCase: GetAllWordsUseCase, // TODO delete
    private val addWordUseCase: AddWordUseCase,
) : StatefulViewModel<CollectionViewModel.State, CollectionViewModel.Event>(State()) {

    data class State(
        val words: List<WordCollectionScreen> = emptyList()
    )

    sealed class Event {
        data class WordAdded(val rus: String, val eng: String) : Event()
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getAllWordsFlowUseCase.execute().collect() { words ->
                produceState(
                    state.copy(words = words.map { it.toWordCollectionScreen() })
                )
            }
        }
    }

    override fun onEvent(event: Event) {
        when (event) {
            is Event.WordAdded -> viewModelScope.launch {
                addWordUseCase.execute(
                    WordDomain(
                        id = generateUUID(),
                        rus = event.rus,
                        eng = event.eng,
                        isLearned = false,
                        isFavorite = false
                    )
                )
            }
        }
    }
}
