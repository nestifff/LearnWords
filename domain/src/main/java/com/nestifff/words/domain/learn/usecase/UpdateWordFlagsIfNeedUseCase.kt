package com.nestifff.words.domain.learn.usecase

import com.nestifff.words.domain.learn.LearnRepository
import com.nestifff.words.domain.word.WordsRepository
import com.nestifff.words.domain.learn.model.WordLearnProcessDomain
import com.nestifff.words.domain.settings.SettingsRepository
import javax.inject.Inject

class UpdateWordFlagsIfNeedUseCase @Inject constructor(
    private val learnRepository: LearnRepository,
    private val wordsRepository: WordsRepository,
    private val settingsRepository: SettingsRepository,
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

        if (word.enteredOnFirstTry >= settingsRepository.getNumberOnFirstTryToMoveInLearned()) {
            wordsRepository.updateWord(
                word.copy(isLearned = true, enteredOnFirstTry = 0)
            )
        } else if (isOnFirstTry) {
            wordsRepository.updateWord(
                word.copy(enteredOnFirstTry = word.enteredOnFirstTry + 1)
            )
        }
    }

}
