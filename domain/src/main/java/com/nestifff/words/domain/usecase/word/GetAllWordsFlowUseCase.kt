package com.nestifff.words.domain.usecase.word

import com.nestifff.words.domain.interfaces.WordsRepository
import com.nestifff.words.domain.model.WordDomain
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllWordsFlowUseCase @Inject constructor(
    private val wordsRepository: WordsRepository
) {

    suspend fun execute(): Flow<List<WordDomain>> {
        return wordsRepository.getWordsFlow()
    }
}
