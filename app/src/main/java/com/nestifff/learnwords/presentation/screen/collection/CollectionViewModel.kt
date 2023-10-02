package com.nestifff.learnwords.presentation.screen.collection

import androidx.lifecycle.viewModelScope
import com.nestifff.learnwords.app.core.BaseViewModel
import com.nestifff.learnwords.app.core.UiEffect
import com.nestifff.learnwords.app.core.UiEvent
import com.nestifff.learnwords.app.core.UiState
import com.nestifff.learnwords.ext.copy
import com.nestifff.learnwords.ext.generateUUID
import com.nestifff.learnwords.presentation.screen.collection.mapper.toWordCollectionScreen
import com.nestifff.learnwords.presentation.screen.collection.mapper.toWordDomain
import com.nestifff.learnwords.presentation.screen.collection.model.AddWordDialogState
import com.nestifff.learnwords.presentation.screen.collection.model.CollectionScreenWord
import com.nestifff.learnwords.presentation.screen.collection.model.CollectionType
import com.nestifff.learnwords.presentation.screen.collection.model.CollectionType.Favorites
import com.nestifff.learnwords.presentation.screen.collection.model.CollectionType.InProgress
import com.nestifff.learnwords.presentation.screen.collection.model.CollectionType.Learned
import com.nestifff.learnwords.presentation.screen.collection.model.ExpandedWordState
import com.nestifff.learnwords.presentation.screen.collection.model.toExpandedState
import com.nestifff.learnwords.presentation.screen.collection.model.change
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
) : BaseViewModel<CollectionViewModel.State, CollectionViewModel.Effect>() {

    data class State(
        val wordsInProgress: ImmutableList<CollectionScreenWord>,
        val wordsLearned: ImmutableList<CollectionScreenWord>,
        val wordsFavorites: ImmutableList<CollectionScreenWord>,
        val currCollectionType: CollectionType,
        val currWordsCollection: ImmutableList<CollectionScreenWord>,
        val addWordDialogState: AddWordDialogState,
        val expandedWordState: ExpandedWordState? = null,
    ) : UiState

    sealed class Effect : UiEffect {
        object NavigateToSettingsScreen : Effect()
        object NavigateToLearnScreen : Effect()
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getAllWordsFlowUseCase.execute().collect() { dbWords ->
                val words = dbWords.map { it.toWordCollectionScreen() }.toImmutableList()
                produceState(
                    state.copy(
                        wordsLearned = words,
                        wordsInProgress = words.copy(),
                        wordsFavorites = words.copy(),
                        currWordsCollection = words,
                    )
                )
            }
        }
    }

    override fun createInitialState(): State = State(
        wordsInProgress = listOf<CollectionScreenWord>().toImmutableList(),
        wordsLearned = listOf<CollectionScreenWord>().toImmutableList(),
        wordsFavorites = listOf<CollectionScreenWord>().toImmutableList(),
        currCollectionType = InProgress,
        currWordsCollection = listOf<CollectionScreenWord>().toImmutableList(),
        addWordDialogState = AddWordDialogState.Hidden,
        expandedWordState = null,
    )

    fun onSettingsClicked() {
        produceEffect(Effect.NavigateToSettingsScreen)
    }

    fun onLearnButtonClicked() {
        produceEffect(Effect.NavigateToLearnScreen)
    }

    fun onWordsInProgressClicked() {
        produceState(state.copy(currCollectionType = InProgress))
    }

    fun onWordsLearnedClicked() {
        produceState(state.copy(currCollectionType = Learned))
    }

    fun onWordsFavoriteClicked() {
        produceState(state.copy(currCollectionType = Favorites))
    }

    fun onWordItemClicked(word: CollectionScreenWord) {
        if (state.currWordsCollection.any { it.id == word.id }) {
            val new = word.toExpandedState().takeIf { state.expandedWordState?.word != word }
            produceState(state.copy(expandedWordState = new))
        }
    }

    fun onWordItemValueChanged(rus: String, eng: String) {
        state.expandedWordState?.let { expanded ->
            produceState(state.copy(expandedWordState = expanded.change(rus = rus, eng = eng)))
        }
    }
    fun onWordUpdateClicked() {
        state.expandedWordState?.let { toUpdate ->
            if (!toUpdate.isChanged) {
                return@let
            }
            viewModelScope.launch {
                updateWordUseCase.execute(toUpdate.word.toWordDomain())
            }
        }
    }

    fun onWordDeleteClicked(word: CollectionScreenWord) {
        viewModelScope.launch {
            deleteWordUseCase.execute(word.id)
        }
    }

    fun onOpenAddWordDialogClicked() {
        produceState(state.copy(addWordDialogState = AddWordDialogState.Expanded.empty()))
    }

    fun onCloseAddWordDialogClicked() {
        produceState(state.copy(addWordDialogState = AddWordDialogState.Hidden))
    }

    fun onAddWordValuesChanged(rus: String, eng: String) {
        val dialogState = state.addWordDialogState
        if (dialogState !is AddWordDialogState.Expanded) {
            return
        }
        produceState(state.copy(addWordDialogState = dialogState.copy(rus = rus, eng = eng)))
    }

    fun onAddWordClicked() {
        val dialogState = state.addWordDialogState
        if (dialogState !is AddWordDialogState.Expanded) {
            return
        }
        viewModelScope.launch {
            addWordUseCase.execute(
                WordDomain(
                    id = generateUUID(),
                    rus = dialogState.rus,
                    eng = dialogState.eng,
                    isLearned = false,
                    isFavorite = false
                )
            )
        }
    }
}
