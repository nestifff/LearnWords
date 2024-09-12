package com.nestifff.learnwords.presentation.screen.result

import androidx.compose.runtime.Stable
import androidx.lifecycle.viewModelScope
import com.nestifff.learnwords.app.core.BaseViewModel
import com.nestifff.learnwords.app.core.UiEffect
import com.nestifff.learnwords.app.core.UiState
import com.nestifff.words.domain.learn.model.LearnProcessResult
import com.nestifff.words.domain.learn.usecase.GetLearnProcessResultUseCase
import kotlinx.coroutines.launch

@Stable
class ResultViewModel(
    private val getLearnProcessResultUseCase: GetLearnProcessResultUseCase
) : BaseViewModel<ResultViewModel.State, ResultViewModel.Effect>() {

    sealed class State : UiState {
        data object Loading : State()
        data class Display(
            val data: LearnProcessResult
        ) : State()
    }

    sealed class Effect : UiEffect {
        data object ReturnToCollectionScreen: Effect()
    }

    init {
        viewModelScope.launch {
            val resultData = getLearnProcessResultUseCase.execute()
            produceState(State.Display(data = resultData))
        }
    }

    fun onReturnToMainClicked() {
        produceEffect(Effect.ReturnToCollectionScreen)
    }

    fun onBackTriggered() {
        produceEffect(Effect.ReturnToCollectionScreen)
    }

    override fun createInitialState(): State = State.Loading
}
