package com.nestifff.words.domain.word.model

data class WordDomain(
    val id: String,
    val rus: String,
    val eng: String,
    val isLearned: Boolean,
    val isFavorite: Boolean,
)
