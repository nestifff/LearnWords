package com.nestifff.words.domain.learn.usecase

import android.util.Log
import com.nestifff.words.domain.learn.LearnRepository
import com.nestifff.words.domain.learn.model.LearnProcessResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetLearnProcessResultUseCase @Inject constructor(
    private val learnRepository: LearnRepository,
) {

    suspend fun execute(): LearnProcessResult = withContext(Dispatchers.Default) {
        val wordToTriesCountMap = learnRepository.getLearnProcessStatisticsMap()

        val wordsAnsweredOnFirstTry = wordToTriesCountMap.filter { it.value == 1 }.keys.toList()

        val mostDifficultWordsWithTriesCount = wordToTriesCountMap.map { Pair(it.key, it.value) }
            .sortedByDescending { it.second }
            .filter { it.second > 1 }

        val wordsBeforeLearnBegin = learnRepository.getWordsBeforeLearnBegin()

        val wordsMovedToLearnedCollection = wordToTriesCountMap.keys.filter { newWord ->
            newWord.isLearned &&
                    // word before process started was in not learned
                    wordsBeforeLearnBegin.find { it.id == newWord.id }?.isLearned == false
        }
        val wordsRemovedFromLearnedCollection = wordToTriesCountMap.keys.filter { newWord ->
            !newWord.isLearned &&
                    wordsBeforeLearnBegin.find { it.id == newWord.id }?.isLearned == true
        }
        val averageTriesCountToAnswerWord = wordToTriesCountMap.values.average()

        return@withContext LearnProcessResult(
            allWordsNum = wordToTriesCountMap.keys.size,
            wordsAnsweredOnFirstTry = wordsAnsweredOnFirstTry,
            mostDifficultWordsWithTriesCount = mostDifficultWordsWithTriesCount,
            wordsMovedToLearnedCollection = wordsMovedToLearnedCollection,
            wordsRemovedFromLearnedCollection = wordsRemovedFromLearnedCollection,
            averageTriesCountToAnswerWord = averageTriesCountToAnswerWord
        )
    }
}
