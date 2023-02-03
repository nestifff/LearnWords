package com.nestifff.words.domain.usecase

import com.nestifff.words.domain.interfaces.WordsRepository
import com.nestifff.words.domain.model.WordDomain
import javax.inject.Inject

class DeleteWordUseCase @Inject constructor(
    private val wordsRepository: WordsRepository
) {

    suspend fun execute(word: WordDomain) {
        return wordsRepository.deleteWord(word)
    }

    suspend fun execute(id: String) {
        return wordsRepository.deleteWord(id)
    }
}