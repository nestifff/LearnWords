package com.nestifff.learnwords.presentation.screen.learn

import androidx.lifecycle.viewModelScope
import com.nestifff.learnwords.app.core.BaseViewModel
import com.nestifff.learnwords.app.core.UiEffect
import com.nestifff.learnwords.app.core.UiState
import com.nestifff.learnwords.app.navigation.destinations.LearnScreenArgument
import com.nestifff.learnwords.presentation.model.toDomain
import com.nestifff.learnwords.presentation.screen.learn.model.LearnButtonState
import com.nestifff.learnwords.presentation.screen.learn.model.LearnNextButtonType.Check
import com.nestifff.learnwords.presentation.screen.learn.model.LearnNextButtonType.Next
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
        val buttonState: LearnButtonState,
        val isTextFieldEnabled: Boolean,
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
                buttonState = state.buttonState.copy(enabled = value.isNotBlank())
            )
        )
    }

    fun onButtonClicked() {
        viewModelScope.launch {
            when (state.buttonState.type) {
                Next -> showNextWord()
                Check -> checkAnswer()
            }
        }
    }

    override fun createInitialState(): State = State(
        isLoading = false,
        word = null,
        buttonState = LearnButtonState(enabled = false, type = Check),
        isTextFieldEnabled = true,
        resulAnimationState = null,
        progressState = LearnProgressIndicatorState(full = arg.wordsNum, done = 0),
    )

    private suspend fun checkAnswer() {
        val word = state.word ?: return
        produceState(
            state.copy(
                isLoading = true,
                isTextFieldEnabled = false,
                buttonState = LearnButtonState(false, Next)
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
                buttonState = LearnButtonState(true, Next),
                progressState = state.progressState.increaseIfCondition(isCorrect)
            )
        )
    }

    private suspend fun showNextWord() {

        produceState(state.copy(isLoading = true, word = null, resulAnimationState = null))

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
