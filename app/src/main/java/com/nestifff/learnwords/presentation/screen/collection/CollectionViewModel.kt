package com.nestifff.learnwords.presentation.screen.collection

import androidx.compose.runtime.Stable
import androidx.lifecycle.viewModelScope
import com.nestifff.learnwords.app.core.BaseViewModel
import com.nestifff.learnwords.app.core.UiEffect
import com.nestifff.learnwords.app.core.UiState
import com.nestifff.learnwords.app.navigation.destinations.LearnScreenArgument
import com.nestifff.learnwords.ext.emptyImmutableList
import com.nestifff.learnwords.presentation.model.CollectionType
import com.nestifff.learnwords.presentation.model.WayToLearn
import com.nestifff.learnwords.presentation.model.fromCollectionIndex
import com.nestifff.learnwords.presentation.model.toIndex
import com.nestifff.learnwords.presentation.model.toUI
import com.nestifff.learnwords.presentation.screen.collection.model.AddWordDialogState
import com.nestifff.learnwords.presentation.screen.collection.model.CollectionItem
import com.nestifff.learnwords.presentation.screen.collection.model.CustomLearnDialogState
import com.nestifff.learnwords.presentation.screen.collection.model.ExpandedWordState
import com.nestifff.learnwords.presentation.screen.collection.model.UndoRemoveWordState
import com.nestifff.learnwords.presentation.screen.collection.model.change
import com.nestifff.learnwords.presentation.screen.collection.model.toExpandedState
import com.nestifff.learnwords.presentation.screen.collection.model.toUI
import com.nestifff.words.domain.collection.usecase.GetAllCollectionsFlowUseCase
import com.nestifff.words.domain.settings.usecase.GetSettingsUseCase
import com.nestifff.words.domain.word.model.NewWordToAddDomain
import com.nestifff.words.domain.word.usecase.AddWordUseCase
import com.nestifff.words.domain.word.usecase.ChangeFavoritePropertyUseCase
import com.nestifff.words.domain.word.usecase.DeleteWordUseCase
import com.nestifff.words.domain.word.usecase.UpdateWordUseCase
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

@Stable
class CollectionViewModel(
    private val getAllCollectionsFlowUseCase: GetAllCollectionsFlowUseCase,
    private val updateWordUseCase: UpdateWordUseCase,
    private val addWordUseCase: AddWordUseCase,
    private val deleteWordUseCase: DeleteWordUseCase,
    private val changeFavoritePropertyUseCase: ChangeFavoritePropertyUseCase,
    private val getSettingsUseCase: GetSettingsUseCase,
) : BaseViewModel<CollectionViewModel.State, CollectionViewModel.Effect>() {

    data class State(
        val collections: ImmutableList<CollectionItem> = emptyImmutableList(),
        val currCollectionType: CollectionType = CollectionType.InProgress,
        val isLearnButtonVisible: Boolean = false,
        val addWordDialogState: AddWordDialogState = AddWordDialogState.Collapsed,
        val removeWordState: UndoRemoveWordState? = null,
        val isUndoRemoveWordVisible: Boolean = false,
        val expandedWordState: ExpandedWordState? = null,
        val customLearnDialogState: CustomLearnDialogState? = null
    ) : UiState

    sealed class Effect : UiEffect {
        data object NavigateToSettingsScreen : Effect()
        data class NavigateToLearnScreen(
            val data: LearnScreenArgument
        ) : Effect()

        data object NotAvailableYetMessage : Effect()
        data object ErrorCreatingWordEmptyValue : Effect()
    }

    init {
        viewModelScope.launch {
            getAllCollectionsFlowUseCase.execute().collect { list ->
                val collections = list.map { it.toUI() }.toImmutableList()
                val currentCollection = collections[state.currCollectionType.toIndex()]
                produceState(
                    state.copy(
                        collections = collections,
                        isLearnButtonVisible = currentCollection.list.isNotEmpty()
                    )
                )
            }
        }
    }

    fun onSettingsClicked() {
        produceEffect(Effect.NavigateToSettingsScreen)
    }

    fun onMenuClicked() {
        produceEffect(Effect.NotAvailableYetMessage)
    }

    fun onLearnButtonClicked() {
        viewModelScope.launch {
            val settings = getSettingsUseCase.execute()
            produceEffect(
                Effect.NavigateToLearnScreen(
                    LearnScreenArgument(
                        wordsCount = settings.defaultNumberToLearn,
                        wayToLearn = settings.defaultWayToLearn.toUI(),
                        collectionType = state.getCurrentCollectionType()
                    )
                )
            )
        }
    }

    fun onLearnButtonLongClicked() {
        // todo cache last entered value
        produceState(
            state.copy(
                customLearnDialogState = CustomLearnDialogState(
                    numberToLearn = 20,
                    wayToLearn = WayToLearn.EngToRus
                )
            )
        )
    }

    fun onCustomLeanDialogDismissed() {
        produceState(state.copy(customLearnDialogState = null))
    }

    fun onCustomLeanDialogNumberChanged(number: Int) {
        val dialogState = state.customLearnDialogState ?: return
        produceState(state.copy(customLearnDialogState = dialogState.copy(numberToLearn = number)))
    }

    fun onCustomLeanDialogLearnClicked() {
        val customLearn = state.customLearnDialogState ?: return
        produceState(state.copy(customLearnDialogState = null))
        produceEffect(
            Effect.NavigateToLearnScreen(
                LearnScreenArgument(
                    wordsCount = customLearn.numberToLearn,
                    wayToLearn = customLearn.wayToLearn,
                    collectionType = state.getCurrentCollectionType()
                )
            )
        )
    }

    fun onNewCollectionTypeSelected(index: Int) {
        if (index != state.currCollectionType.toIndex()) {
            produceState(
                state.copy(
                    currCollectionType = CollectionType.fromCollectionIndex(index),
                    isLearnButtonVisible = state.collections[index].list.isNotEmpty()
                )
            )
        }
    }

    fun onWordItemClicked(id: String) {
        val clickedWord = state.getCurrentCollectionList().find { id == it.id } ?: return
        val new = clickedWord.toExpandedState()
            .takeIf { state.expandedWordState?.word != clickedWord }
        produceState(state.copy(expandedWordState = new))
    }

    fun onMakeFavoriteClicked(id: String) {
        viewModelScope.launch {
            changeFavoritePropertyUseCase.run(id)
        }
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
            updateWordUseCase.execute(
                id = wordState.word.id,
                newRus = wordState.word.rus,
                newEng = wordState.word.eng
            )
            delay(600)
            produceState(state.copy(expandedWordState = null))
        }
    }

    fun onWordDeleteClicked(id: String) {
        viewModelScope.launch {
            deleteWordUseCase.delete(id)
            produceState(state.copy(isUndoRemoveWordVisible = true))
        }
    }

    fun onUndoDeleteClicked() {
        viewModelScope.launch {
            deleteWordUseCase.undo()
            produceState(state.copy(isUndoRemoveWordVisible = false))
        }
    }

    fun undoDeleteWordShownWithoutUndoing() {
        viewModelScope.launch {
            deleteWordUseCase.confirmDelete()
            produceState(state.copy(isUndoRemoveWordVisible = false))
        }
    }

    fun onOpenAddWordDialogClicked() {
        produceState(state.copy(addWordDialogState = AddWordDialogState.Expanded()))
    }

    fun onCloseAddWordDialogClicked() {
        produceState(state.copy(addWordDialogState = AddWordDialogState.Collapsed))
    }

    fun onAddWordValuesChanged(rus: String, eng: String) {
        val dialogState = state.addWordDialogState as? AddWordDialogState.Expanded ?: return
        produceState(state.copy(addWordDialogState = dialogState.copy(rus = rus, eng = eng)))
    }

    fun onAddWordClicked() {
        val dialogState = state.addWordDialogState as? AddWordDialogState.Expanded ?: return
        if (dialogState.eng.isBlank() || dialogState.rus.isBlank()) {
            produceEffect(Effect.ErrorCreatingWordEmptyValue)
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            addWordUseCase.execute(
                NewWordToAddDomain(rus = dialogState.rus, eng = dialogState.eng)
            )
            produceState(state.copy(addWordDialogState = AddWordDialogState.Collapsed))
        }
    }

    override fun createInitialState(): State = State()

    private fun State.getCurrentCollectionType() =
        this.collections[this.currCollectionType.toIndex()].type

    private fun State.getCurrentCollectionList() =
        this.collections[this.currCollectionType.toIndex()].list

    fun onDebugOptionAddWordsClicked() {
        viewModelScope.launch {
            for (i in 0..10) {
                val randomValue = Random.nextInt(0, 1000)
                addWordUseCase.execute(
                    newWord = NewWordToAddDomain(
                        rus = "rus$i $randomValue",
                        eng = "eng$i $randomValue"
                    )
                )
            }
        }
    }
}
