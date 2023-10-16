package com.nestifff.words.domain.usecase.learn

import com.nestifff.words.domain.interfaces.LearnRepository
import com.nestifff.words.domain.model.learn.NextWordResultDomain
import com.nestifff.words.domain.model.learn.WayToLearnDomain.*
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
                    RUS_TO_ENG -> word.rus
                    ENG_TO_RUS -> word.eng
                }
                NextWordResultDomain.Word(valueToShow = valueToShow)
            }
        }
}
