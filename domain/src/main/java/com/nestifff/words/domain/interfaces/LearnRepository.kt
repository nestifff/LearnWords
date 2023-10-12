package com.nestifff.words.domain.interfaces

import com.nestifff.words.domain.model.CollectionTypeDomain
import com.nestifff.words.domain.model.learn.WayToLearnDomain
import com.nestifff.words.domain.model.learn.WordLearnProcessDomain

interface LearnRepository {

    suspend fun setDataForLearning(
        wayToLearn: WayToLearnDomain,
        collectionType: CollectionTypeDomain,
        wordsList: List<WordLearnProcessDomain>
    )

    fun increaseNumberOfTries()

    fun removeFromRemaining()

    suspend fun getRemainingWords(): List<WordLearnProcessDomain>

    fun setNewCurrentWord(word: WordLearnProcessDomain)

    fun getWayToLearn(): WayToLearnDomain

    fun getCurrentWord(): WordLearnProcessDomain

    fun getCurrentWordTries(): Int
}
