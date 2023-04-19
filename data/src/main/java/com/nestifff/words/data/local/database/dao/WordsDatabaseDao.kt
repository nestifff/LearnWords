package com.nestifff.words.data.local.database.dao

import androidx.room.*
import com.nestifff.words.data.local.database.model.WordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WordsDatabaseDao {

    @Query("SELECT * FROM Words")
    suspend fun getWords(): List<WordEntity>

    @Query("SELECT * FROM Words")
    fun getWordsFlow(): Flow<List<WordEntity>>

    @Query("SELECT * FROM Words WHERE id = :id")
    suspend fun getWordById(id: String): WordEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(word: WordEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateWord(word: WordEntity)

    @Delete
    suspend fun deleteWord(word: WordEntity)

    @Query("DELETE FROM Words WHERE id = :id")
    suspend fun deleteWord(id: String)

}
