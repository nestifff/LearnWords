package com.nestifff.words.domain.interfaces

import com.nestifff.words.domain.model.CollectionTypeDomain
import com.nestifff.words.domain.model.WordDomain
import com.nestifff.words.domain.model.learn.WayToLearnDomain
import com.nestifff.words.domain.model.learn.WordToLearnDomain

interface LearnRepository {

    suspend fun createSetForLearning(
        wordsNumber: Int,
        wayToLearnDomain: WayToLearnDomain,
        collectionTypeDomain: CollectionTypeDomain
    )

    suspend fun saveIntermediateResult(isCorrect: Boolean)

    suspend fun getNextWord(): WordToLearnDomain?

    fun getWayToLearn(): WayToLearnDomain

    fun getCurrentWord(): WordDomain
}
