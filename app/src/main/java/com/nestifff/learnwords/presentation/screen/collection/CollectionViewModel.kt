package com.nestifff.learnwords.presentation.screen.collection

import androidx.lifecycle.viewModelScope
import com.nestifff.learnwords.app.core.BaseViewModel
import com.nestifff.learnwords.app.core.UiEffect
import com.nestifff.learnwords.app.core.UiState
import com.nestifff.learnwords.app.navigation.destinations.LearnScreenArgument
import com.nestifff.learnwords.ext.emptyImmutableList
import com.nestifff.learnwords.ext.generateUUID
import com.nestifff.learnwords.ext.immutableListOf
import com.nestifff.learnwords.presentation.model.CollectionType
import com.nestifff.learnwords.presentation.model.WayToLearn
import com.nestifff.learnwords.presentation.screen.collection.model.AddWordDialogState
import com.nestifff.learnwords.presentation.screen.collection.model.CollectionScreenWord
import com.nestifff.learnwords.presentation.screen.collection.model.CustomLearnDialogState
import com.nestifff.learnwords.presentation.screen.collection.model.ExpandedWordState
import com.nestifff.learnwords.presentation.screen.collection.model.change
import com.nestifff.learnwords.presentation.screen.collection.model.toExpandedState
import com.nestifff.learnwords.presentation.screen.collection.model.toWordCollectionScreen
import com.nestifff.learnwords.presentation.screen.collection.model.toWordDomain
import com.nestifff.words.domain.model.WordDomain
import com.nestifff.words.domain.usecase.word.AddWordUseCase
import com.nestifff.words.domain.usecase.word.DeleteWordUseCase
import com.nestifff.words.domain.usecase.word.GetAllWordsFlowUseCase
import com.nestifff.words.domain.usecase.word.UpdateWordUseCase
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
        val collectionTypes: ImmutableList<CollectionType>,
        val currCollectionType: CollectionType,
        val currWordsCollection: ImmutableList<CollectionScreenWord>,
        val addWordDialogState: AddWordDialogState,
        val expandedWordState: ExpandedWordState?,
        val customLearnDialogState: CustomLearnDialogState,
    ) : UiState

    sealed class Effect : UiEffect {
        data object NavigateToSettingsScreen : Effect()
        data class NavigateToLearnScreen(
            val data: LearnScreenArgument
        ) : Effect()
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getAllWordsFlowUseCase.execute().collect { dbWords ->
                val words = dbWords.filter { !it.isLearned }
                    .map { it.toWordCollectionScreen() }
                    .toImmutableList()
                wordsInProgress = words
                wordsFavorites = if (words.size > 10) words.subList(4, 10) else emptyImmutableList()
                wordsLearned = dbWords.filter { it.isLearned }
                    .map { it.toWordCollectionScreen() }
                    .toImmutableList()
                produceState(
                    state.copy(
                        currWordsCollection = words,
                        currCollectionType = CollectionType.InProcess,
                    )
                )
            }
        }
    }

    fun onSettingsClicked() {
        produceEffect(Effect.NavigateToSettingsScreen)
    }

    fun onLearnButtonClicked() {
        produceEffect(Effect.NavigateToLearnScreen(createLearnScreenArgument()))
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
        produceEffect(Effect.NavigateToLearnScreen(createLearnScreenArgument()))
    }

    fun onCollectionTypeClicked(type: CollectionType) {
        val newState = state.copy(
            currCollectionType = type,
            currWordsCollection = when (type) {
                CollectionType.InProcess -> wordsInProgress
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

    override fun createInitialState(): State = State(
        collectionTypes = immutableListOf(
            CollectionType.InProcess,
            CollectionType.Learned,
            CollectionType.Favorite
        ),
        currCollectionType = CollectionType.InProcess,
        currWordsCollection = emptyImmutableList(),
        addWordDialogState = AddWordDialogState.Hidden,
        expandedWordState = null,
        customLearnDialogState = CustomLearnDialogState.Hidden,
    )

    private fun createLearnScreenArgument() =
        LearnScreenArgument(
            wordsNum = 3,
            wayToLearn = WayToLearn.EngToRus,
            collectionType = state.currCollectionType
        )
}
