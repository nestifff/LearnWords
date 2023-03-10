package com.nestifff.words.data.local.database.mapper

import com.nestifff.words.data.local.database.model.WordEntity
import com.nestifff.words.domain.model.WordDomain

fun WordEntity.toWordDomain(): WordDomain =
    WordDomain(
        id = this.id,
        rus = this.rus,
        eng = this.eng,
        isLearned = this.flags.flagsGetIsLearned(),
        isFavorite = this.flags.flagsGetIsFavorite()
    )

fun WordDomain.toWordEntity(): WordEntity =
    WordEntity(
        id = this.id,
        rus = this.rus,
        eng = this.eng,
        flags = createFlags(isFavorite = isFavorite, isLearned = isLearned)
    )
