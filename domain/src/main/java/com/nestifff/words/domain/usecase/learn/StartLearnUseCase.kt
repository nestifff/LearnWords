package com.nestifff.words.domain.usecase.learn

import android.util.Log
import com.nestifff.words.domain.interfaces.LearnRepository
import com.nestifff.words.domain.interfaces.WordsRepository
import com.nestifff.words.domain.model.CollectionTypeDomain
import com.nestifff.words.domain.model.learn.WayToLearnDomain
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
            .onEach {
                Log.i("Lalala", "--- $it")
            }
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
