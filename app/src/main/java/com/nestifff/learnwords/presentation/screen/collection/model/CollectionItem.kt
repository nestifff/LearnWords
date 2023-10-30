package com.nestifff.learnwords.presentation.screen.collection.model

import com.nestifff.learnwords.presentation.model.CollectionType
import com.nestifff.learnwords.presentation.model.toUI
import com.nestifff.words.domain.collection.model.CollectionDomain
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class CollectionItem(
    val type: CollectionType,
    val list: ImmutableList<CollectionWordItem>
)

fun CollectionDomain.toUI(): CollectionItem {
    return CollectionItem(
        type = this.type.toUI(),
        list = this.list.map { it.toCollectionWordItem() }.toImmutableList()
    )
}