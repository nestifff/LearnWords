package com.nestifff.learnwords.presentation.screen.collection

import androidx.lifecycle.viewModelScope
import com.nestifff.learnwords.app.core.StatefulViewModel
import com.nestifff.learnwords.ext.generateUUID
import com.nestifff.learnwords.presentation.screen.collection.mapper.toWordCollectionScreen
import com.nestifff.learnwords.presentation.screen.collection.mapper.toWordDomain
import com.nestifff.learnwords.presentation.screen.collection.model.WordCollectionScreen
import com.nestifff.words.domain.model.WordDomain
import com.nestifff.words.domain.usecase.AddWordUseCase
import com.nestifff.words.domain.usecase.DeleteWordUseCase
import com.nestifff.words.domain.usecase.GetAllWordsFlowUseCase
import com.nestifff.words.domain.usecase.UpdateWordUseCase
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CollectionViewModel(
    private val getAllWordsFlowUseCase: GetAllWordsFlowUseCase,
    private val updateWordUseCase: UpdateWordUseCase,
    private val addWordUseCase: AddWordUseCase,
    private val deleteWordUseCase: DeleteWordUseCase,
) : StatefulViewModel<CollectionViewModel.State, CollectionViewModel.Event>(State()) {

    data class State(
        val words: ImmutableList<WordCollectionScreen> = listOf<WordCollectionScreen>().toImmutableList()
    )

    sealed class Event {
        data class WordAdded(val rus: String, val eng: String) : Event()
        data class WordUpdated(val updatedWord: WordCollectionScreen) : Event()
        data class WordDeleted(val id: String) : Event()
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getAllWordsFlowUseCase.execute().collect() { words ->
                produceState(
                    state.copy(words = words.map { it.toWordCollectionScreen() }.toImmutableList())
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
            is Event.WordUpdated -> viewModelScope.launch {
                updateWordUseCase.execute(event.updatedWord.toWordDomain())
            }
            is Event.WordDeleted -> viewModelScope.launch {
                deleteWordUseCase.execute(event.id)
            }
        }
    }
}
