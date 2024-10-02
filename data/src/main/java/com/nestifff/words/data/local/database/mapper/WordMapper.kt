package com.nestifff.words.data.local.database.mapper

import com.nestifff.words.data.local.database.model.WordEntity
import com.nestifff.words.domain.word.model.WordDomain

fun WordEntity.toWordDomain(): WordDomain =
    WordDomain(
        id = this.id,
        translation = this.translation,
        learningValue = this.learningValue,
        isLearned = this.flags.flagsGetIsLearned(),
        isFavorite = this.flags.flagsGetIsFavorite(),
        enteredOnFirstTry = this.enteredOnFirstTry
    )

fun WordDomain.toWordEntity(): WordEntity =
    WordEntity(
        id = this.id,
        translation = this.translation,
        learningValue = this.learningValue,
        flags = createFlags(isFavorite = isFavorite, isLearned = isLearned),
        enteredOnFirstTry = this.enteredOnFirstTry,
    )
