package com.nestifff.learnwords.ext

import kotlinx.collections.immutable.toImmutableList

fun <T> emptyImmutableList() = listOf<T>().toImmutableList()

fun <T> immutableListOf(vararg elements: T) = listOf(*elements).toImmutableList()
