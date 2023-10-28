package com.nestifff.words.domain.word.usecase

import com.nestifff.words.domain.word.WordsRepository
import com.nestifff.words.domain.word.model.WordDomain
import javax.inject.Inject

class UpdateWordUseCase @Inject constructor(
    private val wordsRepository: WordsRepository
) {

    suspend fun execute(updatedWord: WordDomain) {
        return wordsRepository.updateWord(updatedWord)
    }
}
