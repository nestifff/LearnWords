package com.nestifff.words.domain.word.usecase

import com.nestifff.words.domain.word.WordsRepository
import com.nestifff.words.domain.word.model.WordDomain
import javax.inject.Inject

class UpdateWordUseCase @Inject constructor(
    private val wordsRepository: WordsRepository
) {

    suspend fun execute(id: String, newRus: String, newEng: String) {
        val oldWord = wordsRepository.getWordById(id)!!
        return wordsRepository.updateWord(oldWord.copy(rus = newRus, eng = newEng))
    }
}
