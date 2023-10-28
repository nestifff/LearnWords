package com.nestifff.learnwords.presentation.screen.collection.model

import com.nestifff.words.domain.word.model.WordDomain
import kotlinx.collections.immutable.toImmutableList

data class CollectionScreenWord(
    val id: String,
    val rus: String,
    val eng: String,
    val isFavorite: Boolean,
)

fun WordDomain.toWordCollectionScreen(): CollectionScreenWord =
    CollectionScreenWord(
        id = this.id,
        eng = this.eng,
        rus = this.rus,
        isFavorite = this.isFavorite
    )

fun CollectionScreenWord.toWordDomain(): WordDomain =
    WordDomain(
        id = this.id,
        rus = this.rus,
        eng = this.eng,
        isLearned = false,
        isFavorite = this.isFavorite
    )

fun List<WordDomain>.toWordsCollection() =
    this.map { it.toWordCollectionScreen() }.toImmutableList()

