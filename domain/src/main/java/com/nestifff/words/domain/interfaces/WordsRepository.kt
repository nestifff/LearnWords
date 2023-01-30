package com.nestifff.words.domain.interfaces

import com.nestifff.words.domain.model.WordDomain

interface WordsRepository {

    suspend fun getWords(): List<WordDomain>

    suspend fun getWordById(id: String): WordDomain?

    suspend fun insertWord(word: WordDomain)

    suspend fun updateWord(word: WordDomain)

    suspend fun deleteWord(word: WordDomain)

    suspend fun deleteWord(id: String)

}
