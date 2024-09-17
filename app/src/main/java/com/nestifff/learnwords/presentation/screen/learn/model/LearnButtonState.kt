package com.nestifff.learnwords.presentation.screen.learn.model

data class LearnButtonState(
    val type: LearnNextButtonType,
    val isEnabled: Boolean = false,
    val isLoading: Boolean = false,
)

enum class LearnNextButtonType {
    GoToNextWord, CheckAnswer
}
