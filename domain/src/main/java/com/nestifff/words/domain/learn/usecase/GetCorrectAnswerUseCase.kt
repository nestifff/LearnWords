package com.nestifff.words.domain.learn.usecase

import com.nestifff.words.domain.learn.LearnRepository
import com.nestifff.words.domain.learn.model.WayToLearnDomain
import javax.inject.Inject

class GetCorrectAnswerUseCase @Inject constructor(
    private val learnRepository: LearnRepository,
) {

    operator fun invoke(): String {

        val wayToLearn = learnRepository.getWayToLearn()
        val fullWord = learnRepository.getCurrentWord() ?: throw IllegalStateException()

        return when (wayToLearn) {
            WayToLearnDomain.RUS_TO_ENG -> fullWord.eng
            WayToLearnDomain.ENG_TO_RUS -> fullWord.rus
        }
    }
}
