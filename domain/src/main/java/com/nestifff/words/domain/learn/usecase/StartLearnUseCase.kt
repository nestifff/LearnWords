package com.nestifff.words.domain.learn.usecase

import com.nestifff.words.domain.learn.LearnRepository
import com.nestifff.words.domain.word.WordsRepository
import com.nestifff.words.domain.collection.model.CollectionTypeDomain
import com.nestifff.words.domain.learn.model.WayToLearnDomain
import javax.inject.Inject

class StartLearnUseCase @Inject constructor(
    private val learnRepository: LearnRepository,
    private val wordsRepository: WordsRepository,
) {

    suspend operator fun invoke(
        wordsNumber: Int,
        wayToLearn: WayToLearnDomain,
        collectionType: CollectionTypeDomain
    ) {

        val selectedToLearn = wordsRepository.getWordsLearnProcess()
            .asSequence()
            .filter {
                when (collectionType) {
                    CollectionTypeDomain.IN_PROCESS -> !it.isLearned
                    CollectionTypeDomain.LEARNED -> it.isLearned
                    CollectionTypeDomain.FAVORITE ->
                        throw IllegalArgumentException("Learning Favorite collection isn't supported yet")
                }
            }
            .shuffled()
            .take(wordsNumber)
            .toList()

        learnRepository.setDataForLearning(
            wayToLearn = wayToLearn,
            collectionType = collectionType,
            wordsList = selectedToLearn
        )
    }
}
