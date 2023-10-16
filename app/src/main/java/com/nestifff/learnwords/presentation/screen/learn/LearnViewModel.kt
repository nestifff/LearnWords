package com.nestifff.learnwords.presentation.screen.learn

import androidx.lifecycle.viewModelScope
import com.nestifff.learnwords.app.core.BaseViewModel
import com.nestifff.learnwords.app.core.UiEffect
import com.nestifff.learnwords.app.core.UiState
import com.nestifff.learnwords.app.navigation.destinations.LearnScreenArgument
import com.nestifff.learnwords.ext.emptyString
import com.nestifff.learnwords.presentation.model.toDomain
import com.nestifff.learnwords.presentation.screen.learn.model.LearnProgressIndicatorState
import com.nestifff.learnwords.presentation.screen.learn.model.LearnResultAnimationState
import com.nestifff.learnwords.presentation.screen.learn.model.LearnScreenWordItem
import com.nestifff.learnwords.presentation.screen.learn.model.increaseIfCondition
import com.nestifff.learnwords.presentation.screen.learn.model.toDomain
import com.nestifff.words.domain.model.learn.NextWordResultDomain
import com.nestifff.words.domain.usecase.learn.GetNextWordUseCase
import com.nestifff.words.domain.usecase.learn.ProcessUserAnswerUseCase
import com.nestifff.words.domain.usecase.learn.StartLearnUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LearnViewModel @AssistedInject constructor(
    private val startLearnUseCase: StartLearnUseCase,
    private val getNextWordUseCase: GetNextWordUseCase,
    private val processUserAnswerUseCase: ProcessUserAnswerUseCase,
    @Assisted private val arg: LearnScreenArgument,
) : BaseViewModel<LearnViewModel.State, LearnViewModel.Effect>() {

    data class State(
        val isLoading: Boolean,
        val word: LearnScreenWordItem?,
        val isTextFieldEnabled: Boolean,
        val isNextEnabled: Boolean,
        val resulAnimationState: LearnResultAnimationState?,
        val progressState: LearnProgressIndicatorState,
    ) : UiState

    sealed class Effect : UiEffect {
        data object NavigateToWinScreen : Effect()
    }

    init {
        viewModelScope.launch {
            startLearnUseCase.invoke(
                wordsNumber = arg.wordsNum,
                wayToLearn = arg.wayToLearn.toDomain(),
                collectionType = arg.collectionType.toDomain()
            )
            showNextWord()
        }
    }

    fun onEnteredValueChanged(value: String) {
        val word = state.word ?: return
        produceState(
            state.copy(
                word = word.copy(enteredValue = value),
                isNextEnabled = value.isNotEmpty()
            )
        )
    }

    fun onNextButtonClicked() {
        val word = state.word ?: return
        viewModelScope.launch {
            produceState(
                state.copy(
                    isLoading = true,
                    isTextFieldEnabled = false,
                    isNextEnabled = false
                )
            )
            delay(500)
            val isCorrect = processUserAnswerUseCase.invoke(
                userAnswer = word.toDomain()
            )
            produceState(
                state.copy(
                    isLoading = false,
                    resulAnimationState = if (isCorrect) {
                        LearnResultAnimationState.Right(false)
                    } else {
                        LearnResultAnimationState.Wrong("Some right answer")
                    },
                    word = null,
                )
            )
            delay(500)
            produceState(
                state.copy(
                    resulAnimationState = null,
                    progressState = state.progressState.increaseIfCondition(isCorrect)
                )
            )
            showNextWord()
        }
    }

    override fun createInitialState(): State = State(
        isLoading = false,
        word = null,
        isTextFieldEnabled = true,
        isNextEnabled = false,
        resulAnimationState = null,
        progressState = LearnProgressIndicatorState(full = arg.wordsNum, done = 0),
    )

    private suspend fun showNextWord() {

        produceState(state.copy(isLoading = true, word = null))

        when (val wordResult = getNextWordUseCase.invoke()) {

            is NextWordResultDomain.WordsEnded ->
                produceEffect(Effect.NavigateToWinScreen)

            is NextWordResultDomain.Word ->
                produceState(
                    state.copy(
                        isLoading = false,
                        word = LearnScreenWordItem(wordResult.valueToShow),
                        isTextFieldEnabled = true,
                    )
                )
        }
    }
}
