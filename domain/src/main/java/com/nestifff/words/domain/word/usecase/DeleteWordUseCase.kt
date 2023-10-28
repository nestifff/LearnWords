package com.nestifff.words.domain.word.usecase

import com.nestifff.words.domain.word.WordsRepository
import javax.inject.Inject

class DeleteWordUseCase @Inject constructor(
    private val wordsRepository: WordsRepository
) {

    suspend fun execute(id: String) {
        return wordsRepository.deleteWord(id)
    }
}
