package com.nestifff.words.domain.word.usecase

import android.util.Log
import com.nestifff.words.domain.word.WordsRepository
import com.nestifff.words.domain.word.model.WordDomain
import javax.inject.Inject

class AddWordUseCase @Inject constructor(
    private val wordsRepository: WordsRepository
) {

    suspend fun execute(newWord: WordDomain) {
        Log.i("Lalala", "AddWordUseCase insertWord: $newWord")
        wordsRepository.insertWord(newWord)
    }
}
