package com.nestifff.words.domain.interfaces

import com.nestifff.words.domain.model.WordDomain
import com.nestifff.words.domain.model.learn.WordLearnProcessDomain
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
