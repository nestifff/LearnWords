package com.nestifff.words.domain.learn.usecase

import android.util.Log
import com.nestifff.words.domain.learn.LearnRepository
import com.nestifff.words.domain.learn.model.NextWordResultDomain
import com.nestifff.words.domain.learn.model.WayToLearnDomain.*
import com.nestifff.words.domain.word.model.WordDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetNextWordUseCase @Inject constructor(
    private val repository: LearnRepository
) {

    suspend operator fun invoke(): NextWordResultDomain =
        withContext(Dispatchers.Default) {

            val previousWord = repository.getPreviousWord()
            val remainingWords = repository.getRemainingWords()
            val cantRepeatPrevWord = previousWord != null && remainingWords.size > 1
            val word = if (!cantRepeatPrevWord) {
                remainingWords.randomOrNull()
            } else {
                getNewWordNotSameAsPrevious(previousWord, remainingWords)
            }
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

    private fun getNewWordNotSameAsPrevious(
        prevWord: WordDomain?,
        words: List<WordDomain>
    ): WordDomain? {
        var word: WordDomain
        do {
            word = words.random()
        } while (word == prevWord)
        return word
    }
}
