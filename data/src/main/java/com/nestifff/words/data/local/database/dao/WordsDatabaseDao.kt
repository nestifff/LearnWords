package com.nestifff.words.data.local.database.dao

import androidx.room.*
import com.nestifff.words.data.local.database.model.WordEntity

@Dao
abstract class WordsDatabaseDao {

    @Query("SELECT * FROM Words")
    abstract suspend fun getWords(): List<WordEntity>

    @Query("SELECT * FROM Words WHERE id = :id")
    abstract suspend fun getWordById(id: String): WordEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertWord(word: WordEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun updateWord(word: WordEntity)

    @Delete
    abstract suspend fun deleteWord(word: WordEntity)

    @Query("DELETE FROM Words WHERE id = :id")
    abstract suspend fun deleteWord(id: String)

}
