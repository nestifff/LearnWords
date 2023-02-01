package com.nestifff.words.domain.usecase

import android.util.Log
import com.nestifff.words.domain.interfaces.WordsRepository
import com.nestifff.words.domain.model.WordDomain
import javax.inject.Inject

class AddWordUseCase @Inject constructor(
    private val wordsRepository: WordsRepository
) {

    suspend fun execute(newWord: WordDomain) {
        Log.i("Lalala", "AddWordUseCase insertWord: $newWord")
        wordsRepository.insertWord(newWord)
    }
}
