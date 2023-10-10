package com.nestifff.words.domain.usecase.learn

import com.nestifff.words.domain.interfaces.LearnRepository
import com.nestifff.words.domain.model.learn.WordUserAnswerDomain
import javax.inject.Inject

class ProcessUserAnswerUseCase @Inject constructor(
    private val repository: LearnRepository,
    private val checkIsWordCorrect: CheckIsWordCorrectUseCase,
) {

    suspend operator fun invoke(userAnswer: WordUserAnswerDomain): Boolean {
        val isCorrect = checkIsWordCorrect(userAnswer)
        repository.saveIntermediateResult(isCorrect = isCorrect)
        return isCorrect
    }
}
