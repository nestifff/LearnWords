package com.nestifff.words.data.local.database.repository

import com.nestifff.words.domain.interfaces.LearnRepository
import com.nestifff.words.domain.model.CollectionTypeDomain
import com.nestifff.words.domain.model.learn.WayToLearnDomain
import com.nestifff.words.domain.model.learn.WordLearnProcessDomain
import javax.inject.Inject

class LearnRepositoryImpl @Inject constructor() : LearnRepository {

    private var wordsToTriesToAnswer: MutableMap<WordLearnProcessDomain, Int> = mutableMapOf()
    private var remainingWords: MutableList<WordLearnProcessDomain> = mutableListOf()

    private lateinit var wayToLearn: WayToLearnDomain
    private lateinit var collectionType: CollectionTypeDomain

    private var currentWord: WordLearnProcessDomain? = null

    override suspend fun setDataForLearning(
        wayToLearn: WayToLearnDomain,
        collectionType: CollectionTypeDomain,
        wordsList: List<WordLearnProcessDomain>
    ) {
        this.wordsToTriesToAnswer = wordsList.associateWith { 0 }.toMutableMap()
        this.remainingWords = wordsList.toMutableList()
        this.wayToLearn = wayToLearn
        this.collectionType = collectionType
    }

    override fun increaseNumberOfTries() {
        wordsToTriesToAnswer[currentWord]?.plus(1)
    }

    override fun removeFromRemaining() {
        remainingWords.remove(currentWord)
    }

    override suspend fun getRemainingWords() = remainingWords

    override fun setNewCurrentWord(word: WordLearnProcessDomain?) {
        currentWord = word
    }

    override fun getWayToLearn() = wayToLearn

    override fun getCurrentWord() = currentWord

    override fun getCurrentWordNumberOfTries(): Int? = wordsToTriesToAnswer[currentWord]

    override fun getCurrentWordTries() = wordsToTriesToAnswer[currentWord]!!
}
