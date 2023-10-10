package com.nestifff.learnwords.presentation.screen.learn

import androidx.lifecycle.viewModelScope
import com.nestifff.learnwords.app.core.BaseViewModel
import com.nestifff.learnwords.app.core.UiEffect
import com.nestifff.learnwords.app.core.UiState
import com.nestifff.learnwords.app.navigation.destinations.LearnScreenArgument
import com.nestifff.learnwords.presentation.model.WayToLearn
import com.nestifff.learnwords.presentation.model.toDomain
import com.nestifff.learnwords.presentation.screen.learn.model.LearnScreenWordItem
import com.nestifff.words.domain.usecase.learn.StartLearnUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class LearnViewModel @AssistedInject constructor(
    private val startLearnUseCase: StartLearnUseCase,
    @Assisted arg: LearnScreenArgument,
) : BaseViewModel<LearnViewModel.State, LearnViewModel.Effect>() {

    sealed class State : UiState {

        data object Loading : State()

        data class Display(
            val word: LearnScreenWordItem,
            val mayToLearn: WayToLearn,
            val isCorrect: Boolean? = null,
        ) : State()
    }

    sealed class Effect : UiEffect

    init {
        viewModelScope.launch {
            startLearnUseCase(
                wordsNumber = arg.wordsNum,
                wayToLearn = arg.mayToLearn.toDomain(),
                collectionType = arg.collectionType.toDomain()
            )
        }
    }

    override fun createInitialState(): State = State.Loading

}
