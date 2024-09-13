package com.nestifff.learnwords.presentation.screen.learn

import androidx.lifecycle.viewModelScope
import com.nestifff.learnwords.app.core.BaseViewModel
import com.nestifff.learnwords.app.core.UiEffect
import com.nestifff.learnwords.app.core.UiState
import com.nestifff.learnwords.app.navigation.destinations.LearnScreenArgument
import com.nestifff.learnwords.presentation.model.toDomain
import com.nestifff.learnwords.presentation.screen.learn.model.LearnButtonState
import com.nestifff.learnwords.presentation.screen.learn.model.LearnNextButtonType.CheckAnswer
import com.nestifff.learnwords.presentation.screen.learn.model.LearnNextButtonType.GoToNextWord
import com.nestifff.learnwords.presentation.screen.learn.model.LearnProgressIndicatorState
import com.nestifff.learnwords.presentation.screen.learn.model.UserAnswerResultState
import com.nestifff.learnwords.presentation.screen.learn.model.LearnScreenWordItem
import com.nestifff.learnwords.presentation.screen.learn.model.increaseIfCondition
import com.nestifff.learnwords.presentation.screen.learn.model.toDomain
import com.nestifff.words.domain.learn.model.NextWordResultDomain
import com.nestifff.words.domain.learn.model.UserAnswerFeedback.Correct
import com.nestifff.words.domain.learn.usecase.GetNextWordUseCase
import com.nestifff.words.domain.learn.usecase.ProcessUserAnswerUseCase
import com.nestifff.words.domain.learn.usecase.StartLearnUseCase
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
        val word: LearnScreenWordItem? = null,
        val isEnteringWordEnabled: Boolean = false,
        val progressState: LearnProgressIndicatorState,
        val buttonState: LearnButtonState,
        val resulAnimationState: UserAnswerResultState? = null,
    ) : UiState

    sealed class Effect : UiEffect {
        data object NavigateToResultScreen : Effect()
    }

    init {
        viewModelScope.launch {
            startLearnUseCase.execute(
                wordsCount = arg.wordsCount,
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
                buttonState = state.buttonState.copy(isEnabled = value.isNotBlank())
            )
        )
    }

    fun onButtonClicked() {
        viewModelScope.launch {
            when (state.buttonState.type) {
                GoToNextWord -> showNextWord()
                CheckAnswer -> checkAnswer()
            }
        }
    }

    override fun createInitialState(): State = State(
        progressState = LearnProgressIndicatorState(full = arg.wordsCount, done = 0),
        buttonState = LearnButtonState(isEnabled = false, isLoading = true, type = CheckAnswer)
    )

    private suspend fun checkAnswer() {
        val word = state.word ?: return
        produceState(
            state.copy(
                isEnteringWordEnabled = false,
                buttonState = LearnButtonState(isEnabled = false, isLoading = true, GoToNextWord)
            )
        )
        delay(300)
        val feedback = processUserAnswerUseCase.execute(
            userAnswer = word.toDomain()
        )
        produceState(
            state.copy(
                resulAnimationState = UserAnswerResultState.fromFeedback(feedback),
                buttonState = LearnButtonState(isEnabled = true, isLoading = false, GoToNextWord),
                progressState = state.progressState.increaseIfCondition(feedback is Correct)
            )
        )
    }

    private suspend fun showNextWord() {
        produceState(
            state.copy(
                word = null,
                isEnteringWordEnabled = false,
                buttonState = state.buttonState.copy(isEnabled = false, isLoading = true),
                resulAnimationState = null,
            )
        )

        when (val wordResult = getNextWordUseCase.invoke()) {

            is NextWordResultDomain.WordsEnded ->
                produceEffect(Effect.NavigateToResultScreen)

            is NextWordResultDomain.Word ->
                produceState(
                    state.copy(
                        word = LearnScreenWordItem(wordResult.valueToShow),
                        isEnteringWordEnabled = true,
                        buttonState = LearnButtonState(
                            isEnabled = false,
                            isLoading = false,
                            type = CheckAnswer
                        ),
                    )
                )
        }
    }
}
