package com.nestifff.words.domain.usecase.word

import com.nestifff.words.domain.interfaces.WordsRepository
import javax.inject.Inject

class ChangeFavoritePropertyUseCase @Inject constructor(
    private val repository: WordsRepository,
) {

    suspend fun run(wordId: String) {
        val word = repository.getWordById(wordId) ?: throw IllegalArgumentException()
        repository.updateWord(word.copy(isFavorite = !word.isFavorite))
    }
}
