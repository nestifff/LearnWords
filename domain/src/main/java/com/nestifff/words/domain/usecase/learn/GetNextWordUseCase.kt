package com.nestifff.words.domain.usecase.learn

import com.nestifff.words.domain.interfaces.LearnRepository
import com.nestifff.words.domain.model.learn.WordToLearnDomain
import javax.inject.Inject

class GetNextWordUseCase @Inject constructor(
    private val repository: LearnRepository
) {

    suspend operator fun invoke(): WordToLearnDomain {
        return repository.getNextWord() ?: throw NoSuchElementException()
    }
}
