package com.nestifff.words.data.local.database.repository

import com.nestifff.words.data.local.database.dao.WordsDatabaseDao
import com.nestifff.words.data.local.database.mapper.toWordDomain
import com.nestifff.words.data.local.database.mapper.toWordEntity
import com.nestifff.words.domain.interfaces.WordsRepository
import com.nestifff.words.domain.model.WordDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WordsRepositoryImpl @Inject constructor(
    private val wordsDatabaseDao: WordsDatabaseDao
) : WordsRepository {

    override suspend fun getWords(): List<WordDomain> {
        return wordsDatabaseDao.getWords().map { it.toWordDomain() }
    }

    override suspend fun getWordsFlow(): Flow<List<WordDomain>> {
        return wordsDatabaseDao.getWordsFlow().map { list -> list.map { it.toWordDomain() } }
    }

    override suspend fun getWordById(id: String): WordDomain? {
        return wordsDatabaseDao.getWordById(id)?.toWordDomain()
    }

    override suspend fun insertWord(word: WordDomain) {
        wordsDatabaseDao.insertWord(word.toWordEntity())
    }

    override suspend fun updateWord(word: WordDomain) {
        wordsDatabaseDao.updateWord(word.toWordEntity())
    }

    override suspend fun deleteWord(word: WordDomain) {
        wordsDatabaseDao.deleteWord(word.toWordEntity())
    }

    override suspend fun deleteWord(id: String) {
        wordsDatabaseDao.deleteWord(id)
    }
}
