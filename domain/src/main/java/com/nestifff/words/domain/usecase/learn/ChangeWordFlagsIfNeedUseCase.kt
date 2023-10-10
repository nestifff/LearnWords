package com.nestifff.words.domain.usecase.learn

import com.nestifff.words.domain.interfaces.LearnRepository
import com.nestifff.words.domain.model.learn.WordUserAnswerDomain
import javax.inject.Inject

class ChangeWordFlagsIfNeedUseCase @Inject constructor(
    private val repository: LearnRepository,
) {

    suspend operator fun invoke(userAnswer: WordUserAnswerDomain, isCorrect: Boolean) {
        // TODO
    }

}
