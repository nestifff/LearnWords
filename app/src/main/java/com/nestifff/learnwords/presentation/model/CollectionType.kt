package com.nestifff.learnwords.presentation.model

import com.nestifff.words.domain.collection.model.CollectionTypeDomain

enum class CollectionType {
    InProgress, Learned, Favorite;

    companion object
}

fun CollectionType.Companion.fromCollectionIndex(index: Int): CollectionType {
    return when (index) {
        0 -> CollectionType.InProgress
        1 -> CollectionType.Learned
        2 -> CollectionType.Favorite
        else -> throw RuntimeException("Impossible collection index, $index")
    }
}

fun CollectionType.toIndex(): Int {
    return when (this) {
        CollectionType.InProgress -> 0
        CollectionType.Learned -> 1
        CollectionType.Favorite -> 2
    }
}

fun CollectionType.toDomain(): CollectionTypeDomain {
    return when (this) {
        CollectionType.InProgress -> CollectionTypeDomain.IN_PROGRESS
        CollectionType.Learned -> CollectionTypeDomain.LEARNED
        CollectionType.Favorite -> CollectionTypeDomain.FAVORITE
    }
}

fun CollectionTypeDomain.toUI(): CollectionType {
    return when (this) {
        CollectionTypeDomain.IN_PROGRESS -> CollectionType.InProgress
        CollectionTypeDomain.LEARNED -> CollectionType.Learned
        CollectionTypeDomain.FAVORITE -> CollectionType.Favorite
    }
}

