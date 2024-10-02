package com.nestifff.words.domain.learn.model

import com.nestifff.words.domain.word.model.WordDomain

data class LearnProcessResult(
    val allWordsNum: Int,
    val wordsAnsweredOnFirstTry: List<WordDomain>,
    val mostDifficultWordsWithTriesCount: List<Pair<WordDomain, Int>>,
    val wordsMovedToLearnedCollection: List<WordDomain>,
    val wordsRemovedFromLearnedCollection: List<WordDomain>,
    val averageTriesCountToAnswerWord: Double,
)
