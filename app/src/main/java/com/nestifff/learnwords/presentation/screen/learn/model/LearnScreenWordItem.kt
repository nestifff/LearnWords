package com.nestifff.learnwords.presentation.screen.learn.model

import com.nestifff.learnwords.ext.emptyString

data class LearnScreenWordItem(
    val shownValue: String,
    val enteredValue: String = emptyString(),
)
