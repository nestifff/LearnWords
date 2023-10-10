package com.nestifff.words.domain.usecase.learn

import com.nestifff.words.domain.interfaces.LearnRepository
import com.nestifff.words.domain.model.CollectionTypeDomain
import com.nestifff.words.domain.model.learn.WayToLearnDomain
import javax.inject.Inject

class StartLearnUseCase @Inject constructor(
    private val repository: LearnRepository,
) {

    suspend operator fun invoke(
        wordsNumber: Int,
        wayToLearn: WayToLearnDomain,
        collectionType: CollectionTypeDomain
    ) {
        repository.createSetForLearning(wordsNumber, wayToLearn, collectionType)
    }
}
