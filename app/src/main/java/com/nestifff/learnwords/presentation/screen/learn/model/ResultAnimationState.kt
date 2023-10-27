package com.nestifff.learnwords.presentation.screen.learn.model

import com.nestifff.words.domain.model.learn.UserAnswerFeedback

sealed class ResultAnimationState {

    data class Wrong(
        val rightAnswer: String
    ) : ResultAnimationState()

    data class Right(
        val wasMovedToLearned: Boolean
    ) : ResultAnimationState()

    companion object {

        fun fromFeedback(feedback: UserAnswerFeedback) =
            when (feedback) {
                is UserAnswerFeedback.Correct -> Right(feedback.movedToLearned)
                is UserAnswerFeedback.Wrong -> Wrong(feedback.correctAnswer)
            }
    }
}
