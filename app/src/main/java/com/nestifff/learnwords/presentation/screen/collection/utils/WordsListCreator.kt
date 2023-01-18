package com.nestifff.learnwords.presentation.screen.collection.utils

import com.nestifff.learnwords.presentation.screen.collection.model.Word
import java.util.*

fun getWords(): List<Word> {
    val list: MutableList<Word> = mutableListOf()
    for (i in 0..30) {
        list.add(Word(id = generateUUID(), eng = "val$i", rus = "знач$i", isFavorite = i % 3 == 0))
    }
    return list
}

private fun generateUUID(): String = UUID.randomUUID().toString()