package com.nestifff.words.domain.learn.usecase

import com.nestifff.words.domain.learn.LearnRepository
import com.nestifff.words.domain.learn.model.NextWordResultDomain
import com.nestifff.words.domain.learn.model.WayToLearnDomain.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetNextWordUseCase @Inject constructor(
    private val repository: LearnRepository
) {

    suspend operator fun invoke(): NextWordResultDomain =
        withContext(Dispatchers.Default) {

            val word = repository.getRemainingWords().randomOrNull()
            val wayToLearn = repository.getWayToLearn()
            repository.setNewCurrentWord(word)

            if (word == null) {
                NextWordResultDomain.WordsEnded

            } else {
                val valueToShow = when (wayToLearn) {
                    WRITE_LEARNING_VALUE -> word.translation
                    WRITE_TRANSLATION -> word.learningValue
                }
                NextWordResultDomain.Word(valueToShow = valueToShow)
            }
        }
}
