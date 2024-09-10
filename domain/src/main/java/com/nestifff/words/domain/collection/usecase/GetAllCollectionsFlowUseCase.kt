package com.nestifff.words.domain.collection.usecase

import com.nestifff.words.domain.collection.model.CollectionDomain
import com.nestifff.words.domain.collection.model.CollectionTypeDomain.FAVORITE
import com.nestifff.words.domain.collection.model.CollectionTypeDomain.IN_PROGRESS
import com.nestifff.words.domain.collection.model.CollectionTypeDomain.LEARNED
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetAllCollectionsFlowUseCase @Inject constructor(
    private val getCollectionFlowUseCase: GetCollectionFlowUseCase,
) {

    suspend fun execute(): Flow<List<CollectionDomain>> =

        combine(
            flow = getCollectionFlowUseCase.run(IN_PROGRESS),
            flow2 = getCollectionFlowUseCase.run(LEARNED),
            flow3 = getCollectionFlowUseCase.run(FAVORITE),
            transform = { inProcess, learned, favorite ->
                listOf(
                    CollectionDomain(IN_PROGRESS, inProcess),
                    CollectionDomain(LEARNED, learned),
                    CollectionDomain(FAVORITE, favorite),
                )
            }
        )
}
