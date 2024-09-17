package com.nestifff.learnwords.presentation.screen.learn.model

import com.nestifff.words.domain.learn.model.UserAnswerFeedback

sealed class UserAnswerResultState {

    data class Correct(
        val wasMovedToLearned: Boolean,
    ) : UserAnswerResultState()

    data class CorrectWithTypo(
        val wasMovedToLearned: Boolean,
        val correctAnswer: String,
    ) : UserAnswerResultState()

    data class Wrong(
        val correctAnswer: String,
    ) : UserAnswerResultState()


    fun getCorrectAnswerOrNull(): String? {
        return when(this) {
            is Correct -> null
            is CorrectWithTypo -> this.correctAnswer
            is Wrong -> this.correctAnswer
        }
    }

    fun getWasMovedToLearnedOrNull(): Boolean? {
        return when(this) {
            is Correct -> this.wasMovedToLearned
            is CorrectWithTypo -> this.wasMovedToLearned
            is Wrong -> null
        }
    }

    companion object {

        fun fromFeedback(feedback: UserAnswerFeedback) =
            when (feedback) {
                is UserAnswerFeedback.Correct -> Correct(feedback.wasMovedToLearned)
                is UserAnswerFeedback.Wrong -> Wrong(feedback.correctAnswer)
                is UserAnswerFeedback.CorrectWithTypo -> CorrectWithTypo(
                    feedback.wasMovedToLearned,
                    feedback.correctAnswer
                )
            }
    }
}
