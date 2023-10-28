package com.nestifff.words.domain.learn.model

data class WordLearnProcessDomain(
    val id: String,
    val rus: String,
    val eng: String,
    val enteredOnFirstTry: Int,
    val isLearned: Boolean,
    val isFavorite: Boolean,
)