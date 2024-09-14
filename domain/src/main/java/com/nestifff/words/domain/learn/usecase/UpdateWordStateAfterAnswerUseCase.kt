package com.nestifff.words.domain.learn.usecase

import android.util.Log
import com.nestifff.words.domain.learn.LearnRepository
import com.nestifff.words.domain.settings.SettingsRepository
import com.nestifff.words.domain.word.WordsRepository
import com.nestifff.words.domain.word.model.WordDomain
import javax.inject.Inject

class UpdateWordStateAfterAnswerUseCase @Inject constructor(
    private val learnRepository: LearnRepository,
    private val wordsRepository: WordsRepository,
    private val settingsRepository: SettingsRepository,
) {

    suspend fun execute(isCorrect: Boolean, isOnFirstTry: Boolean) {
        val word = learnRepository.getCurrentWord()!!
        if (word.isLearned) {
            updateForLearnedWord(word, isCorrect)
        } else {
            updateForWordInProcess(
                word,
                isCorrect = isCorrect,
                isOnFirstTry = isOnFirstTry
            )
        }
        learnRepository.refreshWord(word.id)
    }

    private suspend fun updateForLearnedWord(word: WordDomain, isCorrect: Boolean) {
        if (!isCorrect && wordsRepository.getWordById(word.id)!!.isLearned) {
            wordsRepository.updateWord(word.copy(isLearned = false))
        }
    }

    private suspend fun updateForWordInProcess(
        word: WordDomain,
        isCorrect: Boolean,
        isOnFirstTry: Boolean
    ) {
        if (!isCorrect || learnRepository.getWordNumberOfPerformedTries()!! > 1) {
            return
        }

        val requiredOnFirstTry = settingsRepository.getSettings().countOnFirstTryToMoveToLearned
        if (isOnFirstTry && word.enteredOnFirstTry + 1 >= requiredOnFirstTry) {
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
