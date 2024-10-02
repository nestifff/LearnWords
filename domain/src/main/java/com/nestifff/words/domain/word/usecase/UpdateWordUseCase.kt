package com.nestifff.words.domain.word.usecase

import com.nestifff.words.domain.word.WordsRepository
import javax.inject.Inject

class UpdateWordUseCase @Inject constructor(
    private val wordsRepository: WordsRepository
) {

    suspend fun execute(id: String, newTranslation: String, newValue: String) {
        val oldWord = wordsRepository.getWordById(id)!!
        return wordsRepository.updateWord(
            oldWord.copy(
                translation = newTranslation,
                learningValue = newValue
            )
        )
    }
}
