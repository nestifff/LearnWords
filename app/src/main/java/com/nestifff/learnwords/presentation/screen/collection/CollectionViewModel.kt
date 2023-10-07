package com.nestifff.learnwords.presentation.screen.collection

import androidx.lifecycle.viewModelScope
import com.nestifff.learnwords.app.core.BaseViewModel
import com.nestifff.learnwords.app.core.UiEffect
import com.nestifff.learnwords.app.core.UiState
import com.nestifff.learnwords.ext.copy
import com.nestifff.learnwords.ext.emptyImmutableList
import com.nestifff.learnwords.ext.generateUUID
import com.nestifff.learnwords.ext.immutableListOf
import com.nestifff.learnwords.presentation.model.CollectionType
import com.nestifff.learnwords.presentation.screen.collection.mapper.toWordCollectionScreen
import com.nestifff.learnwords.presentation.screen.collection.mapper.toWordDomain
import com.nestifff.learnwords.presentation.screen.collection.model.AddWordDialogState
import com.nestifff.learnwords.presentation.screen.collection.model.CollectionScreenWord
import com.nestifff.learnwords.presentation.screen.collection.model.CustomLearnDialogState
import com.nestifff.learnwords.presentation.screen.collection.model.ExpandedWordState
import com.nestifff.learnwords.presentation.screen.collection.model.change
import com.nestifff.learnwords.presentation.screen.collection.model.toExpandedState
import com.nestifff.words.domain.model.WordDomain
import com.nestifff.words.domain.usecase.AddWordUseCase
import com.nestifff.words.domain.usecase.DeleteWordUseCase
import com.nestifff.words.domain.usecase.GetAllWordsFlowUseCase
import com.nestifff.words.domain.usecase.UpdateWordUseCase
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CollectionViewModel(
    private val getAllWordsFlowUseCase: GetAllWordsFlowUseCase,
    private val updateWordUseCase: UpdateWordUseCase,
    private val addWordUseCase: AddWordUseCase,
    private val deleteWordUseCase: DeleteWordUseCase,
) : BaseViewModel<CollectionViewModel.State, CollectionViewModel.Effect>() {

    private var wordsInProgress: ImmutableList<CollectionScreenWord> = emptyImmutableList()
    private var wordsLearned: ImmutableList<CollectionScreenWord> = emptyImmutableList()
    private var wordsFavorites: ImmutableList<CollectionScreenWord> = emptyImmutableList()

    data class State(
        val collectionTypes: ImmutableList<CollectionType> = immutableListOf(
            CollectionType.InProgress,
            CollectionType.Learned,
            CollectionType.Favorite
        ),
        val currCollectionType: CollectionType,
        val currWordsCollection: ImmutableList<CollectionScreenWord>,
        val addWordDialogState: AddWordDialogState,
        val expandedWordState: ExpandedWordState? = null,
        val customLearnDialogState: CustomLearnDialogState,
    ) : UiState

    sealed class Effect : UiEffect {
        data object NavigateToSettingsScreen : Effect()
        data object NavigateToLearnScreen : Effect()
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getAllWordsFlowUseCase.execute().collect { dbWords ->
                val words = dbWords.map { it.toWordCollectionScreen() }.toImmutableList()
                wordsInProgress = words
                wordsFavorites = words.subList(4, 10)
                wordsLearned = words.subList(3, 5)
                produceState(
                    state.copy(
                        currWordsCollection = words,
                        currCollectionType = CollectionType.InProgress,
                    )
                )
            }
        }
    }

    override fun createInitialState(): State = State(
        currCollectionType = CollectionType.InProgress,
        currWordsCollection = emptyImmutableList(),
        addWordDialogState = AddWordDialogState.Hidden,
        expandedWordState = null,
        customLearnDialogState = CustomLearnDialogState.Hidden,
    )

    fun onSettingsClicked() {
        produceEffect(Effect.NavigateToSettingsScreen)
    }

    fun onLearnButtonClicked() {
        produceEffect(Effect.NavigateToLearnScreen)
    }

    fun onLearnButtonLongClicked() {
        produceState(state.copy(customLearnDialogState = CustomLearnDialogState.Expanded(20)))
    }

    fun onCustomLeanDialogDismissed() {
        produceState(state.copy(customLearnDialogState = CustomLearnDialogState.Hidden))
    }

    fun onCustomLeanDialogNumberChanged(number: Int) {
        val dialogState = state.customLearnDialogState
        if (dialogState !is CustomLearnDialogState.Expanded) {
            return
        }
        produceState(state.copy(customLearnDialogState = dialogState.copy(numberToLearn = number)))
    }

    fun onCustomLeanDialogLearnClicked() {
        produceEffect(Effect.NavigateToLearnScreen)
    }

    fun onCollectionTypeClicked(type: CollectionType) {
        val newState = state.copy(
            currCollectionType = type,
            currWordsCollection = when(type) {
                CollectionType.InProgress -> wordsInProgress
                CollectionType.Learned -> wordsLearned
                CollectionType.Favorite -> wordsFavorites
            }
        )
        produceState(newState)
    }

    fun onWordItemClicked(id: String) {
        val clickedWord = state.currWordsCollection.find { id == it.id } ?: return
        val new = clickedWord.toExpandedState()
            .takeIf { state.expandedWordState?.word != clickedWord }
        produceState(state.copy(expandedWordState = new))
    }

    fun onEditWordValuesChanged(rus: String, eng: String) {
        state.expandedWordState?.let { expanded ->
            produceState(state.copy(expandedWordState = expanded.change(rus = rus, eng = eng)))
        }
    }

    fun onWordUpdateClicked() {
        val wordState = state.expandedWordState ?: return
        if (!wordState.isSaveEnabled) {
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            produceState(
                state.copy(
                    expandedWordState = wordState.copy(
                        isLoading = true, isSaveEnabled = false
                    )
                )
            )
            updateWordUseCase.execute(wordState.word.toWordDomain())
            delay(600)
            produceState(state.copy(expandedWordState = null))
        }
    }

    fun onWordDeleteClicked(id: String) {
        viewModelScope.launch {
            deleteWordUseCase.execute(id)
        }
    }

    fun onOpenAddWordDialogClicked() {
        produceState(state.copy(addWordDialogState = AddWordDialogState.Expanded()))
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
        viewModelScope.launch(Dispatchers.IO) {
            addWordUseCase.execute(
                WordDomain(
                    id = generateUUID(),
                    rus = dialogState.rus,
                    eng = dialogState.eng,
                    isLearned = false,
                    isFavorite = false
                )
            )
            produceState(state.copy(addWordDialogState = AddWordDialogState.Hidden))
        }
    }
}
