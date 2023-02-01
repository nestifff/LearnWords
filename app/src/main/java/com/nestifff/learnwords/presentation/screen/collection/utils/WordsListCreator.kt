package com.nestifff.learnwords.presentation.screen.collection.utils

import com.nestifff.learnwords.presentation.screen.collection.model.WordCollectionScreen
import java.util.*
import javax.inject.Inject

// TODO delete class make just function
class WordsListCreator @Inject constructor() {

    fun getWords(): List<WordCollectionScreen> {
        val list: MutableList<WordCollectionScreen> = mutableListOf()
        for (i in 0..30) {
            list.add(WordCollectionScreen(id = generateUUID(), eng = "val$i", rus = "знач$i", isFavorite = i % 3 == 0))
        }
        return list
    }

    private fun generateUUID(): String = UUID.randomUUID().toString()
}
