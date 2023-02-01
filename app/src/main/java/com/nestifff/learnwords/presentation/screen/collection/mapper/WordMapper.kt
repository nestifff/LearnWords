package com.nestifff.learnwords.presentation.screen.collection.mapper

import com.nestifff.learnwords.presentation.screen.collection.model.WordCollectionScreen
import com.nestifff.words.domain.model.WordDomain


fun WordDomain.toWordCollectionScreen(): WordCollectionScreen =
    WordCollectionScreen(
        id = this.id,
        eng = this.eng,
        rus = this.rus,
        isFavorite = this.isFavorite
    )

fun WordCollectionScreen.toWordDomain(): WordDomain =
    WordDomain(
        id = this.id,
        rus = this.rus,
        eng = this.eng,
        isLearned = false,
        isFavorite =  this.isFavorite
    )
