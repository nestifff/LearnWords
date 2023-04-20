package com.nestifff.learnwords.presentation.screen.collection.mapper

import com.nestifff.learnwords.presentation.screen.collection.model.CollectionScreenWord
import com.nestifff.words.domain.model.WordDomain


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
        isFavorite =  this.isFavorite
    )
