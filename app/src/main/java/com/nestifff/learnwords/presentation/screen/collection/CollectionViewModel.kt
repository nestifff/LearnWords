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
) : BaseViewModel<CollectionViewModel.State, CollectionViewModel.Event, CollectionViewModel.Effect>() {

    data class State(
        val wordsInProgress: ImmutableList<CollectionScreenWord>,
        val wordsLearned: ImmutableList<CollectionScreenWord>,
        val wordsFavorites: ImmutableList<CollectionScreenWord>,
        val currCollectionType: CollectionType,
        val currWordsCollection: ImmutableList<CollectionScreenWord>,
        val addWordDialogState: AddWordDialogState,
        val expandedWordState: ExpandedWordState? = null,
    ) : UiState

    sealed class Event : UiEvent {
        object SettingsClicked : Event()
        object LearnButtonClicked : Event()

        object WordsInProgressClicked : Event()
        object WordsLearnedClicked : Event()
        object WordsFavoriteClicked : Event()

        data class WordItemClicked(val word: CollectionScreenWord) : Event()
        data class WordItemValueChanged(val rus: String, val eng: String) : Event()
        object WordUpdateClicked : Event()

        data class WordDeleteClicked(val word: CollectionScreenWord) : Event()

        object OpenAddWordDialogClicked : Event()
        object CloseAddWordDialogClicked : Event()
        data class AddWordValuesChanged(val rus: String, val eng: String) : Event()
        object AddWordClicked : Event()
    }

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

    override fun onEvent(event: Event) {
        when (event) {
            is Event.SettingsClicked -> produceEffect(Effect.NavigateToSettingsScreen)
            is Event.LearnButtonClicked -> produceEffect(Effect.NavigateToLearnScreen)

            is Event.WordsInProgressClicked -> produceState(state.copy(currCollectionType = InProgress))
            is Event.WordsLearnedClicked -> produceState(state.copy(currCollectionType = Learned))
            is Event.WordsFavoriteClicked -> produceState(state.copy(currCollectionType = Favorites))

            is Event.WordItemClicked -> expandCollectionItem(word = event.word)
            is Event.WordItemValueChanged -> changeExpandedWord(rus = event.rus, eng = event.eng)
            is Event.WordUpdateClicked -> updateExpandedWordInDb()

            is Event.WordDeleteClicked -> viewModelScope.launch {
                deleteWordUseCase.execute(event.word.id)
            }

            is Event.OpenAddWordDialogClicked -> produceState(
                state.copy(addWordDialogState = AddWordDialogState.Expanded.empty())
            )

            is Event.CloseAddWordDialogClicked -> produceState(
                state.copy(addWordDialogState = AddWordDialogState.Hidden)
            )
            is Event.AddWordValuesChanged ->
                changeAddWordDialogValues(rus = event.rus, eng = event.eng)
            is Event.AddWordClicked -> addWordToDb()
        }
    }

    private fun expandCollectionItem(word: CollectionScreenWord) {
        if (state.currWordsCollection.any { it.id == word.id }) {
            val new = word.toExpandedState().takeIf { state.expandedWordState?.word != word }
            produceState(state.copy(expandedWordState = new))
        }
    }

    private fun changeExpandedWord(rus: String, eng: String) {
        state.expandedWordState?.let { expanded ->
            produceState(state.copy(expandedWordState = expanded.change(rus = rus, eng = eng)))
        }
    }

    private fun updateExpandedWordInDb() {
        state.expandedWordState?.let { toUpdate ->
            if (!toUpdate.isChanged) {
                return@let
            }
            viewModelScope.launch {
                updateWordUseCase.execute(toUpdate.word.toWordDomain())
            }
        }
    }

    private fun changeAddWordDialogValues(rus: String, eng: String) {
        val dialogState = state.addWordDialogState
        if (dialogState !is AddWordDialogState.Expanded) {
            return
        }
        produceState(state.copy(addWordDialogState = dialogState.copy(rus = rus, eng = eng)))
    }

    private fun addWordToDb() {
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
