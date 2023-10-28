package com.nestifff.words.domain.learn.model


sealed class UserAnswerFeedback {

    data class Correct(
        val movedToLearned: Boolean,
    ) : UserAnswerFeedback()

    data class Wrong(
        val correctAnswer: String,
    ) : UserAnswerFeedback()
}
