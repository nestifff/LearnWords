package com.nestifff.words.domain.collection.usecase

import com.nestifff.words.domain.collection.model.CollectionTypeDomain
import com.nestifff.words.domain.collection.model.CollectionTypeDomain.FAVORITE
import com.nestifff.words.domain.collection.model.CollectionTypeDomain.IN_PROCESS
import com.nestifff.words.domain.collection.model.CollectionTypeDomain.LEARNED
import com.nestifff.words.domain.word.WordsRepository
import com.nestifff.words.domain.word.model.WordDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCollectionFlowUseCase @Inject constructor(
    private val repository: WordsRepository,
) {

    suspend fun run(collectionType: CollectionTypeDomain): Flow<List<WordDomain>> =

        repository.getWordsFlow().map { list ->
            list.filter {
                when (collectionType) {
                    IN_PROCESS -> !it.isLearned
                    LEARNED -> it.isLearned
                    FAVORITE -> it.isFavorite
                }
            }
        }
}
