package com.nestifff.words.data.local.database.mapper

import com.nestifff.words.data.local.database.model.WordEntity
import com.nestifff.words.domain.learn.model.WordLearnProcessDomain

fun WordEntity.toWordLearnProcessDomain(): WordLearnProcessDomain =
    WordLearnProcessDomain(
        id = this.id,
        rus = this.rus,
        eng = this.eng,
        isLearned = this.flags.flagsGetIsLearned(),
        isFavorite = this.flags.flagsGetIsFavorite(),
        enteredOnFirstTry = this.enteredOnFirstTry
    )

fun WordLearnProcessDomain.toWordEntity(): WordEntity =
    WordEntity(
        id = this.id,
        rus = this.rus,
        eng = this.eng,
        flags = createFlags(isFavorite = isFavorite, isLearned = isLearned),
        enteredOnFirstTry = this.enteredOnFirstTry
    )
