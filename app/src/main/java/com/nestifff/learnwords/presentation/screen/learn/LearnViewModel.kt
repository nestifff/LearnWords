package com.nestifff.learnwords.presentation.screen.learn

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.nestifff.learnwords.app.core.BaseViewModel
import com.nestifff.learnwords.app.core.UiEffect
import com.nestifff.learnwords.app.core.UiState
import com.nestifff.learnwords.app.navigation.destinations.LearnScreenArgument
import com.nestifff.learnwords.ext.emptyString
import com.nestifff.learnwords.presentation.model.WayToLearn
import com.nestifff.learnwords.presentation.model.toDomain
import com.nestifff.learnwords.presentation.screen.learn.model.LearnResultAnimationState
import com.nestifff.learnwords.presentation.screen.learn.model.LearnScreenWordItem
import com.nestifff.learnwords.presentation.screen.learn.model.toDomain
import com.nestifff.words.domain.model.learn.NextWordResultDomain
import com.nestifff.words.domain.usecase.learn.GetNextWordUseCase
import com.nestifff.words.domain.usecase.learn.ProcessUserAnswerUseCase
import com.nestifff.words.domain.usecase.learn.StartLearnUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LearnViewModel @AssistedInject constructor(
    private val startLearnUseCase: StartLearnUseCase,
    private val getNextWordUseCase: GetNextWordUseCase,
    private val processUserAnswerUseCase: ProcessUserAnswerUseCase,
    @Assisted private val arg: LearnScreenArgument,
) : BaseViewModel<LearnViewModel.State, LearnViewModel.Effect>() {

    sealed class State : UiState {

        data object Loading : State()

        data class Display(
            val word: LearnScreenWordItem,
            val wayToLearn: WayToLearn,
            val isCorrect: Boolean? = null,
            val isLoading: Boolean = false,
            val resulAnimationState: LearnResultAnimationState? = null,
        ) : State()
    }

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

    override fun createInitialState(): State = State.Loading

    fun onEnteredValueChanged(value: String) {
        val currState = state as? State.Display ?: return
        produceState(currState.copy(word = currState.word.copy(enteredValue = value)))
    }

    fun onNextButtonClicked() {
        val currState = state as? State.Display ?: return
        viewModelScope.launch {
            produceState(currState.copy(isLoading = true))
            delay(500)
            val isCorrect = processUserAnswerUseCase.invoke(
                userAnswer = currState.word.toDomain()
            )
            produceState(
                currState.copy(
                    isLoading = false,
                    resulAnimationState = if (isCorrect) {
                        LearnResultAnimationState.Right(false)
                    } else {
                        LearnResultAnimationState.Wrong(emptyString())
                    }
                )
            )
            delay(500)
            produceState(currState.copy(resulAnimationState = null))
        }
    }

    private suspend fun showNextWord() = withContext(Dispatchers.Default) {
        val wordResult = getNextWordUseCase.invoke()
        Log.i("Lalala", "showNextWord: wordResult = $wordResult")
        when (wordResult) {

            is NextWordResultDomain.WordsEnded ->
                produceEffect(Effect.NavigateToWinScreen)

            is NextWordResultDomain.Word -> {
                val word = LearnScreenWordItem(wordResult.valueToShow)

                val newState = when (val currState = state) {
                    is State.Display -> currState.copy(
                        word = word,
                        isCorrect = null,
                    )

                    is State.Loading -> State.Display(
                        word = word,
                        wayToLearn = arg.wayToLearn,
                        isCorrect = null,
                    )
                }
                produceState(newState)
            }
        }
    }

}
