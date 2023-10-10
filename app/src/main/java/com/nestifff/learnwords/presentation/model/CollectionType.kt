package com.nestifff.learnwords.presentation.model

import com.nestifff.words.domain.model.CollectionTypeDomain

enum class CollectionType {
    InProgress, Learned, Favorite
}

fun CollectionType.toDomain(): CollectionTypeDomain {
    return when (this) {
        CollectionType.InProgress -> CollectionTypeDomain.IN_PROGRESS
        CollectionType.Learned -> CollectionTypeDomain.LEARNED
        CollectionType.Favorite -> CollectionTypeDomain.FAVORITE
    }
}
