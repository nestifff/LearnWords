package com.nestifff.words.domain.usecase.word

import com.nestifff.words.domain.word.WordsRepository
import com.nestifff.words.domain.word.model.WordDomain
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllWordsFlowUseCase @Inject constructor(
    private val wordsRepository: WordsRepository
) {

    suspend fun execute(): Flow<List<WordDomain>> {
        return wordsRepository.getWordsFlow()
    }
}
