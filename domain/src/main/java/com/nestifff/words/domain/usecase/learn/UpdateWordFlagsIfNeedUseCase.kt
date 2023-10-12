package com.nestifff.words.domain.usecase.learn

import com.nestifff.words.domain.interfaces.LearnRepository
import com.nestifff.words.domain.interfaces.WordsRepository
import com.nestifff.words.domain.model.learn.WordLearnProcessDomain
import javax.inject.Inject

class UpdateWordFlagsIfNeedUseCase @Inject constructor(
    private val learnRepository: LearnRepository,
    private val wordsRepository: WordsRepository,
) {

    suspend operator fun invoke(isCorrect: Boolean) {
        val word = learnRepository.getCurrentWord()
        if (word.isLearned) {
            updateForLearnedWord(word)
        } else {
            updateForWordInProcess(word, isCorrect)
        }
    }

    private suspend fun updateForLearnedWord(word: WordLearnProcessDomain) {
        if (learnRepository.getCurrentWordTries() > 0
            && wordsRepository.getWordById(word.id)!!.isLearned
        ) {
            wordsRepository.updateWord(word.copy(isLearned = false))
        }
    }

    private suspend fun updateForWordInProcess(word: WordLearnProcessDomain, isCorrect: Boolean) {
        if (!isCorrect || learnRepository.getCurrentWordTries() > 0) {
            return
        }
        if (word.enteredOnFirstTry >= NUMBER_OF_SUCCESS_ON_FIRST_TRY_TO_MAKE_LEARNED) {
            wordsRepository.updateWord(word.copy(isLearned = true, enteredOnFirstTry = 0))
        }
    }

    companion object {
        // todo move to sp
        // todo connect to settings
        private const val NUMBER_OF_SUCCESS_ON_FIRST_TRY_TO_MAKE_LEARNED = 2
    }

}
