package com.nestifff.words.domain.learn.usecase

import com.nestifff.words.domain.learn.LearnRepository
import com.nestifff.words.domain.word.WordsRepository
import com.nestifff.words.domain.learn.model.UserAnswerFeedback
import com.nestifff.words.domain.learn.model.WordUserAnswerDomain
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
