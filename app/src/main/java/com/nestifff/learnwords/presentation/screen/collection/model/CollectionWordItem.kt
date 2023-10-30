package com.nestifff.learnwords.presentation.screen.collection.model

import com.nestifff.words.domain.word.model.WordDomain

data class CollectionWordItem(
    val id: String,
    val rus: String,
    val eng: String,
    val isFavorite: Boolean,
)

fun WordDomain.toCollectionWordItem(): CollectionWordItem =
    CollectionWordItem(
        id = this.id,
        eng = this.eng,
        rus = this.rus,
        isFavorite = this.isFavorite
    )

fun CollectionWordItem.toWordDomain(): WordDomain =
    WordDomain(
        id = this.id,
        rus = this.rus,
        eng = this.eng,
        isLearned = false,
        isFavorite = this.isFavorite
    )
