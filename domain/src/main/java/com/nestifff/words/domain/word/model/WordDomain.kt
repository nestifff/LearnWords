package com.nestifff.words.domain.word.model

data class WordDomain(
    val id: String,
    val translation: String,
    val learningValue: String,
    val enteredOnFirstTry: Int,
    val isLearned: Boolean,
    val isFavorite: Boolean,
)
