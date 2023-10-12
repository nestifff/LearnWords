package com.nestifff.words.domain.usecase.learn

import com.nestifff.words.domain.interfaces.LearnRepository
import com.nestifff.words.domain.model.learn.WordUserAnswerDomain
import javax.inject.Inject

class ProcessUserAnswerUseCase @Inject constructor(
    private val repository: LearnRepository,
    private val checkIsWordCorrectUseCase: CheckIsWordCorrectUseCase,
    private val updateWordFlagsIfNeedUseCase: UpdateWordFlagsIfNeedUseCase,
) {

    suspend operator fun invoke(userAnswer: WordUserAnswerDomain): Boolean {
        val isCorrect = checkIsWordCorrectUseCase(userAnswer)
        if (isCorrect) {
            repository.removeFromRemaining()
        } else {
            repository.increaseNumberOfTries()
        }
        updateWordFlagsIfNeedUseCase(isCorrect)
        return isCorrect
    }
}
