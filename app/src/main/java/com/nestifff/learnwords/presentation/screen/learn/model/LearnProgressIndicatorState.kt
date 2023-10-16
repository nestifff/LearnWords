package com.nestifff.learnwords.presentation.screen.learn.model

data class LearnProgressIndicatorState(
    val full: Int,
    val done: Int,
)

fun LearnProgressIndicatorState.increaseIfCondition(condition: Boolean): LearnProgressIndicatorState {
    return if (condition) {
        this.copy(done = this.done + 1)
    } else {
        this.copy()
    }
}
