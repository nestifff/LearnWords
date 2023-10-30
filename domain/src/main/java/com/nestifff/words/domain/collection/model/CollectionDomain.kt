package com.nestifff.words.domain.collection.model

import com.nestifff.words.domain.word.model.WordDomain

data class CollectionDomain(
    val type: CollectionTypeDomain,
    val list: List<WordDomain>,
)
