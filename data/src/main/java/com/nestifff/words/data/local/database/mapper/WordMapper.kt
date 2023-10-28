package com.nestifff.words.data.local.database.mapper

import com.nestifff.words.data.local.database.model.WordEntity
import com.nestifff.words.domain.word.model.WordDomain

fun WordEntity.toWordDomain(): WordDomain =
    WordDomain(
        id = this.id,
        rus = this.rus,
        eng = this.eng,
        isLearned = this.flags.flagsGetIsLearned(),
        isFavorite = this.flags.flagsGetIsFavorite()
    )

fun WordDomain.toWordEntity(enteredOnFirstTry: Int): WordEntity =
    WordEntity(
        id = this.id,
        rus = this.rus,
        eng = this.eng,
        flags = createFlags(isFavorite = isFavorite, isLearned = isLearned),
        enteredOnFirstTry = enteredOnFirstTry,
    )
