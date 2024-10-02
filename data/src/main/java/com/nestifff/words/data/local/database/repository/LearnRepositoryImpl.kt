package com.nestifff.words.data.local.database.repository

import android.util.Log
import com.nestifff.words.domain.learn.LearnRepository
import com.nestifff.words.domain.collection.model.CollectionTypeDomain
import com.nestifff.words.domain.learn.model.WayToLearnDomain
import com.nestifff.words.domain.word.WordsRepository
import com.nestifff.words.domain.word.model.WordDomain
import javax.inject.Inject

class LearnRepositoryImpl @Inject constructor(
    private val wordsRepository: WordsRepository
) : LearnRepository {

    private var wordsToTriesToAnswer: MutableMap<WordDomain, Int> = mutableMapOf()
    private var remainingWords: MutableList<WordDomain> = mutableListOf()
    private var wordsBeforeLearnBegin: MutableList<WordDomain> = mutableListOf()

    private lateinit var wayToLearn: WayToLearnDomain
    private lateinit var collectionType: CollectionTypeDomain

    private var currentWord: WordDomain? = null
    private var previousWord: WordDomain? = null

    override suspend fun setDataForLearning(
        wayToLearn: WayToLearnDomain,
        collectionType: CollectionTypeDomain,
        wordsList: List<WordDomain>
    ) {
        this.wordsToTriesToAnswer = wordsList.associateWith { 0 }.toMutableMap()
        this.remainingWords = wordsList.toMutableList()
        // deep copy for list of words before begin
        this.wordsBeforeLearnBegin = wordsList.map { it.copy() }.toMutableList()
        this.wayToLearn = wayToLearn
        this.collectionType = collectionType
    }

    override fun addOnePerformedTryToWord() {
        wordsToTriesToAnswer[currentWord!!] = wordsToTriesToAnswer[currentWord]!! + 1
    }

    override fun removeWordFromRemaining() {
        remainingWords.remove(currentWord)
    }

    override suspend fun getRemainingWords() = remainingWords

    override fun setNewCurrentWord(word: WordDomain?) {
        previousWord = currentWord
        currentWord = word
    }

    override fun getWayToLearn() = wayToLearn

    override fun getCurrentWord() = currentWord

    override fun getPreviousWord(): WordDomain? = previousWord

    override suspend fun refreshWord(id: String) {
        val updatedWord = wordsRepository.getWordById(id)!!

        // refresh currentWord if needed
        if (currentWord?.id == id) {
            currentWord = updatedWord
        }

        // refresh in wordsToTriesToAnswer
        val outdatedWord = wordsToTriesToAnswer.keys.find { it.id == id }!!
        val value = wordsToTriesToAnswer[outdatedWord]!!
        wordsToTriesToAnswer.remove(outdatedWord)
        wordsToTriesToAnswer[updatedWord] = value

        // refresh in remainingWords
        val wordWasExistingInList = remainingWords.remove(outdatedWord)
        if (wordWasExistingInList) {
            remainingWords.add(updatedWord)
        }
    }

    override fun getWordNumberOfPerformedTries(): Int? = wordsToTriesToAnswer[currentWord]

    override fun getLearnProcessStatisticsMap(): Map<WordDomain, Int> = wordsToTriesToAnswer

    override fun getWordsBeforeLearnBegin(): List<WordDomain> = wordsBeforeLearnBegin
}
