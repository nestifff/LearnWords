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

    // todo: mb return here all the info - next work, words left, etc and get rid of GetNextWordUseCase
    suspend fun execute(userAnswer: WordUserAnswerDomain): UserAnswerFeedback {

        val isCorrect = checkIsWordCorrectUseCase.getIsCorrect(userAnswer)
        val isOnFirstTry = isCorrect && learnRepo.getWordNumberOfPerformedTries() == 0
        learnRepo.addOnePerformedTryToWord()
        if (isCorrect) {
            learnRepo.removeWordFromRemaining()
        }

        val wasInLearnedPreviously = learnRepo.getCurrentWord()?.isLearned == true
        updateWordStateAfterAnswerUseCase.execute(isCorrect, isOnFirstTry)
        val isNowInLearned = learnRepo.getCurrentWord()?.isLearned == true

        val correctAnswer = getCorrectAnswerUseCase.execute()
        return if (isCorrect) {
            val wasMovedToLearned = !wasInLearnedPreviously && isNowInLearned
            val isContainsTypo = checkIsWordCorrectUseCase.getIsContainsTypos(userAnswer)
            if (isContainsTypo) {
                UserAnswerFeedback.CorrectWithTypo(wasMovedToLearned, correctAnswer)
            } else {
                UserAnswerFeedback.Correct(wasMovedToLearned)
            }
        } else {
            UserAnswerFeedback.Wrong(correctAnswer)
        }
    }
}
