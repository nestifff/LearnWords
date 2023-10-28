package com.nestifff.words.domain.collection.model

import com.nestifff.words.domain.word.model.WordDomain

data class AllCollectionsHolder(
    val inProcess: List<WordDomain>,
    val learned: List<WordDomain>,
    val favorite: List<WordDomain>,
)
