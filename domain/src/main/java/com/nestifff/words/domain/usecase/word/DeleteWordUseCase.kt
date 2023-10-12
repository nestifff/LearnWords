package com.nestifff.words.domain.usecase.word

import com.nestifff.words.domain.interfaces.WordsRepository
import javax.inject.Inject

class DeleteWordUseCase @Inject constructor(
    private val wordsRepository: WordsRepository
) {

    suspend fun execute(id: String) {
        return wordsRepository.deleteWord(id)
    }
}
