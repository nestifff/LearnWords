package com.nestifff.words.domain.usecase

import com.nestifff.words.domain.interfaces.WordsRepository
import com.nestifff.words.domain.model.WordDomain
import javax.inject.Inject

class UpdateWordUseCase @Inject constructor(
    private val wordsRepository: WordsRepository
) {

    suspend fun execute(updatedWord: WordDomain) {
        return wordsRepository.updateWord(updatedWord)
    }
}
