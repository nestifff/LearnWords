package com.nestifff.learnwords.presentation.screen.collection.model

import com.nestifff.words.domain.learn.model.WayToLearnDomain

data class CustomLearnDialogState(
    val numberToLearnStr: String,
    val wayToLearn: WayToLearnDomain,
    val isWayToLearnMenuVisible: Boolean
)
