package com.nestifff.words.domain.word

import com.nestifff.words.domain.word.model.WordDomain
import kotlinx.coroutines.flow.Flow

interface WordsRepository {

    suspend fun getWords(): List<WordDomain>

    suspend fun getWordsFlow(): Flow<List<WordDomain>>

    suspend fun getWordById(id: String): WordDomain?

    suspend fun insertWord(word: WordDomain)

    suspend fun updateWord(word: WordDomain)

    suspend fun deleteWord(id: String)

    suspend fun undoDeleteWord()

    suspend fun confirmDeleteWord()
}
