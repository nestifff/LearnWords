package com.nestifff.learnwords.presentation.screen.learn.model

data class LearnButtonState(
    val enabled: Boolean,
    val type: LearnNextButtonType,
)

enum class LearnNextButtonType {
    Next, Check
}
