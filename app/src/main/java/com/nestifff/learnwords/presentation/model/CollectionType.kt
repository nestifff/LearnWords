package com.nestifff.learnwords.presentation.model

import com.nestifff.words.domain.collection.model.CollectionTypeDomain

enum class CollectionType {
    InProcess, Learned, Favorite
}

fun CollectionType.toDomain(): CollectionTypeDomain {
    return when (this) {
        CollectionType.InProcess -> CollectionTypeDomain.IN_PROCESS
        CollectionType.Learned -> CollectionTypeDomain.LEARNED
        CollectionType.Favorite -> CollectionTypeDomain.FAVORITE
    }
}

fun CollectionTypeDomain.toUI(): CollectionType {
    return when (this) {
        CollectionTypeDomain.IN_PROCESS -> CollectionType.InProcess
        CollectionTypeDomain.LEARNED -> CollectionType.Learned
        CollectionTypeDomain.FAVORITE -> CollectionType.Favorite
    }
}
