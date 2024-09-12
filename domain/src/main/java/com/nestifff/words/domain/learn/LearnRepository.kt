package com.nestifff.words.domain.learn

import com.nestifff.words.domain.collection.model.CollectionTypeDomain
import com.nestifff.words.domain.learn.model.WayToLearnDomain
import com.nestifff.words.domain.word.model.WordDomain

interface LearnRepository {

    suspend fun setDataForLearning(
        wayToLearn: WayToLearnDomain,
        collectionType: CollectionTypeDomain,
        wordsList: List<WordDomain>
    )

    fun addOnePerformedTryToWord()

    fun removeWordFromRemaining()

    suspend fun getRemainingWords(): List<WordDomain>

    fun setNewCurrentWord(word: WordDomain?)

    fun getWayToLearn(): WayToLearnDomain

    fun getCurrentWord(): WordDomain?

    suspend fun refreshWord(id: String)

    fun getWordNumberOfPerformedTries(): Int?

    fun getLearnProcessStatisticsMap(): Map<WordDomain, Int>

    fun getWordsBeforeLearnBegin(): List<WordDomain>
}
