package com.nestifff.words.domain.learn.usecase

import com.nestifff.words.domain.learn.LearnRepository
import com.nestifff.words.domain.word.WordsRepository
import com.nestifff.words.domain.learn.model.WordLearnProcessDomain
import javax.inject.Inject

class UpdateWordFlagsIfNeedUseCase @Inject constructor(
    private val learnRepository: LearnRepository,
    private val wordsRepository: WordsRepository,
) {

    suspend operator fun invoke(isCorrect: Boolean, isOnFirstTry: Boolean) {
        val word = learnRepository.getCurrentWord() ?: throw IllegalStateException()
        if (word.isLearned) {
            updateForLearnedWord(word, isCorrect)
        } else {
            updateForWordInProcess(
                word,
                isCorrect = isCorrect,
                isOnFirstTry = isOnFirstTry
            )
        }
    }

    private suspend fun updateForLearnedWord(word: WordLearnProcessDomain, isCorrect: Boolean) {
        if (!isCorrect && wordsRepository.getWordById(word.id)!!.isLearned) {
            wordsRepository.updateWord(word.copy(isLearned = false))
        }
    }

    private suspend fun updateForWordInProcess(
        word: WordLearnProcessDomain,
        isCorrect: Boolean,
        isOnFirstTry: Boolean
    ) {
        if (!isCorrect || learnRepository.getCurrentWordTries() > 0) {
            return
        }

        if (word.enteredOnFirstTry >= NUMBER_OF_SUCCESS_ON_FIRST_TRY_TO_MAKE_LEARNED) {
            wordsRepository.updateWord(
                word.copy(isLearned = true, enteredOnFirstTry = 0)
            )
        } else if (isOnFirstTry) {
            wordsRepository.updateWord(
                word.copy(enteredOnFirstTry = word.enteredOnFirstTry + 1)
            )
        }
    }

    companion object {
        // todo move to sp
        // todo connect to settings
        private const val NUMBER_OF_SUCCESS_ON_FIRST_TRY_TO_MAKE_LEARNED = 2
    }
}
