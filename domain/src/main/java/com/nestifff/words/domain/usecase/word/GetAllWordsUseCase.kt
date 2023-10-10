package com.nestifff.words.domain.usecase.word

import com.nestifff.words.domain.interfaces.WordsRepository
import com.nestifff.words.domain.model.WordDomain
import javax.inject.Inject

class GetAllWordsUseCase @Inject constructor(
    private val wordsRepository: WordsRepository
) {

    suspend fun execute(): List<WordDomain> {
        return wordsRepository.getWords()
    }
}
