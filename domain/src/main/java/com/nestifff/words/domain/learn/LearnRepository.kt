package com.nestifff.words.domain.learn

import com.nestifff.words.domain.collection.model.CollectionTypeDomain
import com.nestifff.words.domain.learn.model.WayToLearnDomain
import com.nestifff.words.domain.learn.model.WordLearnProcessDomain

interface LearnRepository {

    suspend fun setDataForLearning(
        wayToLearn: WayToLearnDomain,
        collectionType: CollectionTypeDomain,
        wordsList: List<WordLearnProcessDomain>
    )

    fun increaseNumberOfTries()

    fun removeFromRemaining()

    suspend fun getRemainingWords(): List<WordLearnProcessDomain>

    fun setNewCurrentWord(word: WordLearnProcessDomain?)

    fun getWayToLearn(): WayToLearnDomain

    fun getCurrentWord(): WordLearnProcessDomain?

    fun getCurrentWordNumberOfTries(): Int?

    fun getCurrentWordTries(): Int
}
