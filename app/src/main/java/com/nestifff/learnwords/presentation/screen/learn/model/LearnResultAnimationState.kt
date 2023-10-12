package com.nestifff.learnwords.presentation.screen.learn.model

sealed class LearnResultAnimationState {

    data class Wrong(
        val rightAnswer: String
    ) : LearnResultAnimationState()

    data class Right(
        val wasMovedToLearned: Boolean
    ) : LearnResultAnimationState()
}
