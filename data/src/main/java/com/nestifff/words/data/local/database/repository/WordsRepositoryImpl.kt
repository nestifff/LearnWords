package com.nestifff.words.data.local.database.repository

import com.nestifff.words.data.local.database.dao.WordsDatabaseDao
import com.nestifff.words.data.local.database.mapper.toWordDomain
import com.nestifff.words.data.local.database.mapper.toWordEntity
import com.nestifff.words.data.local.database.model.WordEntity
import com.nestifff.words.domain.word.WordsRepository
import com.nestifff.words.domain.word.model.WordDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WordsRepositoryImpl @Inject constructor(
    private val wordsDatabaseDao: WordsDatabaseDao
) : WordsRepository {

    private var wordWithDeletingCanBeUndo: WordEntity? = null

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
        wordsDatabaseDao.updateWord(
            word.toWordEntity()
        )
    }

    override suspend fun deleteWord(id: String) {
        val wordEntity = wordsDatabaseDao.getWordById(id)
        wordsDatabaseDao.deleteWord(id)
        wordWithDeletingCanBeUndo = wordEntity
    }

    override suspend fun undoDeleteWord() {
        wordsDatabaseDao.insertWord(wordWithDeletingCanBeUndo!!)
        wordWithDeletingCanBeUndo = null
    }

    override suspend fun confirmDeleteWord() {
        wordWithDeletingCanBeUndo = null
    }
}
