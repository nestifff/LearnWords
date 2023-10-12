package com.nestifff.words.data.local.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Words")
data class WordEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo
    val rus: String,
    @ColumnInfo
    val eng: String,
    @ColumnInfo(defaultValue = "0")
    val enteredOnFirstTry: Int,
    // 2^0 = isFavorite, 2^1 = isLearned
    // example : 00000010 = 2 (isFavorite = false, isLearned = true)
    @ColumnInfo
    val flags: Int,
)
