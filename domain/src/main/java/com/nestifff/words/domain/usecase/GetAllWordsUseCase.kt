package com.nestifff.words.domain.usecase

import com.nestifff.words.domain.interfaces.WordsRepository
import com.nestifff.words.domain.model.WordDomain
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllWordsUseCase @Inject constructor(
    private val wordsRepository: WordsRepository
) {

    suspend fun execute(): List<WordDomain> {
        return wordsRepository.getWords()
    }
}
