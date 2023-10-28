package com.nestifff.words.domain.word

import com.nestifff.words.domain.word.model.WordDomain
import com.nestifff.words.domain.learn.model.WordLearnProcessDomain
import kotlinx.coroutines.flow.Flow

interface WordsRepository {

    suspend fun getWords(): List<WordDomain>

    suspend fun getWordsLearnProcess(): List<WordLearnProcessDomain>

    suspend fun getWordsFlow(): Flow<List<WordDomain>>

    suspend fun getWordById(id: String): WordDomain?

    suspend fun insertWord(word: WordDomain)

    suspend fun updateWord(word: WordDomain)

    suspend fun updateWord(word: WordLearnProcessDomain)

    suspend fun deleteWord(id: String)

}
