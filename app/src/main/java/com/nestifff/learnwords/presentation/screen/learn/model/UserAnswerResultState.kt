package com.nestifff.learnwords.presentation.screen.learn.model

import com.nestifff.words.domain.learn.model.UserAnswerFeedback

sealed class UserAnswerResultState {

    data class Wrong(
        val correctAnswer: String
    ) : UserAnswerResultState()

    data class Correct(
        val wasMovedToLearned: Boolean
    ) : UserAnswerResultState()

    companion object {

        fun fromFeedback(feedback: UserAnswerFeedback) =
            when (feedback) {
                is UserAnswerFeedback.Correct -> Correct(feedback.wasMovedToLearned)
                is UserAnswerFeedback.Wrong -> Wrong(feedback.correctAnswer)
            }
    }
}
