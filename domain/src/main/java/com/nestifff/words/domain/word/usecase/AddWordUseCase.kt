package com.nestifff.words.domain.word.usecase

import com.nestifff.words.domain.word.WordsRepository
import com.nestifff.words.domain.word.model.NewWordToAddDomain
import com.nestifff.words.domain.word.model.WordDomain
import java.util.UUID
import javax.inject.Inject

class AddWordUseCase @Inject constructor(
    private val wordsRepository: WordsRepository
) {

    suspend fun execute(newWord: NewWordToAddDomain) {
        val mappedWord = WordDomain(
            id = UUID.randomUUID().toString(),
            rus = newWord.rus,
            eng = newWord.eng,
            enteredOnFirstTry = 0,
            isFavorite = false,
            isLearned = false
        )
        wordsRepository.insertWord(mappedWord)
    }
}
