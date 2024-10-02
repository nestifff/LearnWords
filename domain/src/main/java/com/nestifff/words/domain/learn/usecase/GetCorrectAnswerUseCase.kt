package com.nestifff.words.domain.learn.usecase

import com.nestifff.words.domain.learn.LearnRepository
import com.nestifff.words.domain.learn.model.WayToLearnDomain
import javax.inject.Inject

class GetCorrectAnswerUseCase @Inject constructor(
    private val learnRepository: LearnRepository,
) {

    fun execute(): String {

        val wayToLearn = learnRepository.getWayToLearn()
        val fullWord = learnRepository.getCurrentWord() ?: throw IllegalStateException()

        return when (wayToLearn) {
            WayToLearnDomain.WRITE_LEARNING_VALUE -> fullWord.learningValue
            WayToLearnDomain.WRITE_TRANSLATION -> fullWord.translation
        }
    }
}
