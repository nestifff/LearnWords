package com.nestifff.learnwords.presentation.screen.learn.model

data class LearnProgressIndicatorState(
    val allWordsCount: Int = 0,
    val doneWordsCount: Int = 0,
)

fun LearnProgressIndicatorState.increaseIfCondition(condition: Boolean): LearnProgressIndicatorState {
    return if (condition) {
        this.copy(doneWordsCount = this.doneWordsCount + 1)
    } else {
        this.copy()
    }
}
