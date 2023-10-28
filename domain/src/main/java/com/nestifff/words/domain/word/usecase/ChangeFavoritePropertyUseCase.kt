package com.nestifff.words.domain.word.usecase

import com.nestifff.words.domain.word.WordsRepository
import javax.inject.Inject

class ChangeFavoritePropertyUseCase @Inject constructor(
    private val repository: WordsRepository,
) {

    suspend fun run(wordId: String) {
        val word = repository.getWordById(wordId) ?: throw IllegalArgumentException()
        repository.updateWord(word.copy(isFavorite = !word.isFavorite))
    }
}
