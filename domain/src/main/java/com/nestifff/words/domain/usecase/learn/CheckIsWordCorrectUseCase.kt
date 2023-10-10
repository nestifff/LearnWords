package com.nestifff.words.domain.usecase.learn

import com.nestifff.words.domain.interfaces.LearnRepository
import com.nestifff.words.domain.model.learn.WayToLearnDomain
import com.nestifff.words.domain.model.learn.WordUserAnswerDomain
import javax.inject.Inject

class CheckIsWordCorrectUseCase @Inject constructor(
    private val learnRepository: LearnRepository
) {

    operator fun invoke(userAnswer: WordUserAnswerDomain): Boolean {
        val wayToLearn = learnRepository.getWayToLearn()
        val fullWord = learnRepository.getCurrentWord()
        val correctValue = when (wayToLearn) {
            WayToLearnDomain.RUS_TO_ENG -> fullWord.eng
            WayToLearnDomain.ENG_TO_RUS -> fullWord.rus
        }
        return compareIfCorrect(entered = userAnswer.enteredValue, correct = correctValue)
    }

    private fun compareIfCorrect(entered: String, correct: String): Boolean {
        return entered.trim().lowercase() == correct.trim().lowercase()
    }
}
