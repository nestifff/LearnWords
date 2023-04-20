package com.nestifff.learnwords.ext

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

fun <T> ImmutableList<T>.copy(): ImmutableList<T> {
    return this.toList().toImmutableList()
}
