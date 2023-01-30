package com.nestifff.words.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nestifff.words.data.local.database.dao.WordsDatabaseDao
import com.nestifff.words.data.local.database.model.WordEntity


@Database(
    entities = [WordEntity::class],
    version = 1,
    exportSchema = true,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun wordsDao(): WordsDatabaseDao
}
