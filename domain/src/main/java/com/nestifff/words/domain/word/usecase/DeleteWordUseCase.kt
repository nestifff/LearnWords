package com.nestifff.words.domain.word.usecase

import com.nestifff.words.domain.word.WordsRepository
import javax.inject.Inject

class DeleteWordUseCase @Inject constructor(
    private val wordsRepository: WordsRepository
) {

    suspend fun delete(id: String) {
        return wordsRepository.deleteWord(id)
    }

    suspend fun undo() {
        wordsRepository.undoDeleteWord()
    }

    suspend fun confirmDelete() {
        wordsRepository.confirmDeleteWord()
    }
}
