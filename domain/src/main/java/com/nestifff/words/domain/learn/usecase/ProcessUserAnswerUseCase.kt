package com.nestifff.words.domain.learn.usecase

import com.nestifff.words.domain.learn.LearnRepository
import com.nestifff.words.domain.learn.model.UserAnswerFeedback
import com.nestifff.words.domain.learn.model.WordUserAnswerDomain
import javax.inject.Inject

class ProcessUserAnswerUseCase @Inject constructor(
    private val learnRepo: LearnRepository,
    private val checkIsWordCorrectUseCase: CheckIsWordCorrectUseCase,
    private val updateWordStateAfterAnswerUseCase: UpdateWordStateAfterAnswerUseCase,
    private val getCorrectAnswerUseCase: GetCorrectAnswerUseCase,
) {

    suspend fun execute(userAnswer: WordUserAnswerDomain): UserAnswerFeedback {

        val isCorrect = checkIsWordCorrectUseCase.execute(userAnswer)
        val isOnFirstTry = isCorrect && learnRepo.getWordNumberOfPerformedTries() == 0
        learnRepo.addOnePerformedTryToWord()
        if (isCorrect) {
            learnRepo.removeWordFromRemaining()
        }

        val wasInLearnedPreviously = learnRepo.getCurrentWord()?.isLearned == true
        updateWordStateAfterAnswerUseCase.execute(isCorrect, isOnFirstTry)
        val isNowInLearned = learnRepo.getCurrentWord()?.isLearned == true

        return if (isCorrect) {
            val wasMovedToLearned = !wasInLearnedPreviously && isNowInLearned
            UserAnswerFeedback.Correct(wasMovedToLearned)
        } else {
            val correctAnswer = getCorrectAnswerUseCase.execute()
            UserAnswerFeedback.Wrong(correctAnswer)
        }
    }
}
