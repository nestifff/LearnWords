package com.nestifff.words.domain.model.learn

data class WordLearnProcessDomain(
    val id: String,
    val rus: String,
    val eng: String,
    val enteredOnFirstTry: Int,
    val isLearned: Boolean,
    val isFavorite: Boolean,
)