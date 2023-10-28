package com.nestifff.learnwords.presentation.screen.learn.model

import com.nestifff.learnwords.ext.emptyString
import com.nestifff.words.domain.learn.model.WordUserAnswerDomain

data class LearnScreenWordItem(
    val shownValue: String,
    val enteredValue: String = emptyString(),
)

fun LearnScreenWordItem.toDomain() =
    WordUserAnswerDomain(enteredValue = this.enteredValue)
