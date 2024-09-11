package com.nestifff.words.data.local.database.repository

import com.nestifff.words.domain.learn.LearnRepository
import com.nestifff.words.domain.collection.model.CollectionTypeDomain
import com.nestifff.words.domain.learn.model.WayToLearnDomain
import com.nestifff.words.domain.word.model.WordDomain
import javax.inject.Inject

class LearnRepositoryImpl @Inject constructor() : LearnRepository {

    private var wordsToTriesToAnswer: MutableMap<WordDomain, Int> = mutableMapOf()
    private var remainingWords: MutableList<WordDomain> = mutableListOf()

    private lateinit var wayToLearn: WayToLearnDomain
    private lateinit var collectionType: CollectionTypeDomain

    private var currentWord: WordDomain? = null

    override suspend fun setDataForLearning(
        wayToLearn: WayToLearnDomain,
        collectionType: CollectionTypeDomain,
        wordsList: List<WordDomain>
    ) {
        this.wordsToTriesToAnswer = wordsList.associateWith { 0 }.toMutableMap()
        this.remainingWords = wordsList.toMutableList()
        this.wayToLearn = wayToLearn
        this.collectionType = collectionType
    }

    override fun addOnePerformedTryToWord() {
        wordsToTriesToAnswer[currentWord]?.plus(1)
    }

    override fun removeWordFromRemaining() {
        remainingWords.remove(currentWord)
    }

    override suspend fun getRemainingWords() = remainingWords

    override fun setNewCurrentWord(word: WordDomain?) {
        currentWord = word
    }

    override fun getWayToLearn() = wayToLearn

    override fun getCurrentWord() = currentWord

    override fun getWordNumberOfPerformedTries(): Int? = wordsToTriesToAnswer[currentWord]
}
