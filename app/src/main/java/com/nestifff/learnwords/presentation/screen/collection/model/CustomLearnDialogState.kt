package com.nestifff.learnwords.presentation.screen.collection.model

import com.nestifff.learnwords.presentation.model.WayToLearn

sealed class CustomLearnDialogState {

    data object Hidden : CustomLearnDialogState()

    data class Expanded(
        val numberToLearn: Int,
        val wayToLearn: WayToLearn,
    ) : CustomLearnDialogState()
}
