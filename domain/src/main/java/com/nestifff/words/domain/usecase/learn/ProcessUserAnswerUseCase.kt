package com.nestifff.words.domain.usecase.learn

import com.nestifff.words.domain.interfaces.LearnRepository
import com.nestifff.words.domain.interfaces.WordsRepository
import com.nestifff.words.domain.model.learn.UserAnswerFeedback
import com.nestifff.words.domain.model.learn.WordUserAnswerDomain
import javax.inject.Inject

class ProcessUserAnswerUseCase @Inject constructor(
    private val learnRepo: LearnRepository,
    private val wordsRepo: WordsRepository,
    private val checkIsWordCorrectUseCase: CheckIsWordCorrectUseCase,
    private val updateWordFlagsIfNeedUseCase: UpdateWordFlagsIfNeedUseCase,
    private val getCorrectAnswerUseCase: GetCorrectAnswerUseCase,
) {

    suspend operator fun invoke(userAnswer: WordUserAnswerDomain): UserAnswerFeedback {

        val isCorrect = checkIsWordCorrectUseCase.invoke(userAnswer)
        val isOnFirstTry = isCorrect && learnRepo.getCurrentWordNumberOfTries() == 0
        if (isCorrect) {
            learnRepo.removeFromRemaining()
        } else {
            learnRepo.increaseNumberOfTries()
        }

        updateWordFlagsIfNeedUseCase.invoke(isCorrect, isOnFirstTry)

        val id = learnRepo.getCurrentWord()?.id ?: throw IllegalStateException()
        val movedToLearned = isCorrect && wordsRepo.getWordById(id)?.isLearned == true
        val correctAnswer = getCorrectAnswerUseCase.invoke()

        return if (isCorrect) {
            UserAnswerFeedback.Correct(movedToLearned)
        } else {
            UserAnswerFeedback.Wrong(correctAnswer)
        }
    }
}
