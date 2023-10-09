package com.nestifff.learnwords.presentation.screen.learn

import com.nestifff.learnwords.app.core.BaseViewModel
import com.nestifff.learnwords.app.core.UiEffect
import com.nestifff.learnwords.app.core.UiState
import com.nestifff.learnwords.presentation.model.WayToLearn
import com.nestifff.learnwords.presentation.screen.learn.model.LearnScreenWordItem

class LearnViewModel(
//    wordsNum: Int,
//    mayToLearn: WayToLearn,
//    collectionType: CollectionType,
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

    override fun createInitialState(): State = State.Loading
}
