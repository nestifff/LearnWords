package com.nestifff.words.data.local.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameColumn
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import com.nestifff.words.data.local.database.dao.WordsDatabaseDao
import com.nestifff.words.data.local.database.model.WordEntity


@Database(
    entities = [WordEntity::class],
    version = 3,
    exportSchema = true,
    autoMigrations = [
        AutoMigration (from = 1, to = 2),
        AutoMigration (from = 2, to = 3, spec = AppDatabase.Migration2To3::class),
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun wordsDao(): WordsDatabaseDao

    @RenameColumn(tableName = "Words", fromColumnName = "rus", toColumnName = "translation")
    @RenameColumn(tableName = "Words", fromColumnName = "eng", toColumnName = "learning_value")
    class Migration2To3 : AutoMigrationSpec
}
