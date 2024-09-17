package com.nestifff.words.domain.learn.usecase

import com.nestifff.words.domain.learn.LearnRepository
import com.nestifff.words.domain.word.WordsRepository
import com.nestifff.words.domain.collection.model.CollectionTypeDomain
import com.nestifff.words.domain.learn.model.LearnProcessStartedData
import com.nestifff.words.domain.learn.model.WayToLearnDomain
import javax.inject.Inject

class StartLearnUseCase @Inject constructor(
    private val learnRepository: LearnRepository,
    private val wordsRepository: WordsRepository,
) {

    suspend fun execute(
        wordsCount: Int,
        wayToLearn: WayToLearnDomain,
        collectionType: CollectionTypeDomain
    ): LearnProcessStartedData {
        val selectedToLearn = wordsRepository.getWords()
            .asSequence()
            .filter {
                when (collectionType) {
                    CollectionTypeDomain.IN_PROGRESS -> !it.isLearned
                    CollectionTypeDomain.LEARNED -> it.isLearned
                    CollectionTypeDomain.FAVORITE ->
                        throw IllegalArgumentException("Learning Favorite collection isn't supported yet")
                }
            }
            .shuffled()
            .take(wordsCount)
            .toList()

        learnRepository.setDataForLearning(
            wayToLearn = wayToLearn,
            collectionType = collectionType,
            wordsList = selectedToLearn
        )

        return LearnProcessStartedData(allWordsInSetCount = selectedToLearn.size)
    }
}
