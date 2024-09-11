package com.nestifff.learnwords.presentation.screen.learn.model

data class LearnButtonState(
    val isEnabled: Boolean,
    val isLoading: Boolean,
    val type: LearnNextButtonType,
)

enum class LearnNextButtonType {
    GoToNextWord, CheckAnswer
}
