package com.nestifff.words.domain.learn.usecase

import com.nestifff.words.domain.learn.model.WordUserAnswerDomain
import javax.inject.Inject

class CheckIsWordCorrectUseCase @Inject constructor(
    private val getCorrectAnswerUseCase: GetCorrectAnswerUseCase,
) {

    operator fun invoke(userAnswer: WordUserAnswerDomain): Boolean {
        val correctValue = getCorrectAnswerUseCase.invoke()
        return compareIfCorrect(entered = userAnswer.enteredValue, correct = correctValue)
    }

    private fun compareIfCorrect(entered: String, correct: String): Boolean {
        return entered.trim().lowercase() == correct.trim().lowercase()
    }
}
