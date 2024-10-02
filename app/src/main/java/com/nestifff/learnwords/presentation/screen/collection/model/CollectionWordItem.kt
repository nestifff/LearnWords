package com.nestifff.learnwords.presentation.screen.collection.model

import com.nestifff.words.domain.word.model.WordDomain

data class CollectionWordItem(
    val id: String,
    val translation: String,
    val value: String,
    val isFavorite: Boolean,
)

fun WordDomain.toCollectionWordItem(): CollectionWordItem =
    CollectionWordItem(
        id = this.id,
        value = this.learningValue,
        translation = this.translation,
        isFavorite = this.isFavorite
    )
