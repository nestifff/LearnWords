package com.nestifff.learnwords.presentation.screen.collection.model

sealed class CustomLearnDialogState {

    data object Hidden : CustomLearnDialogState()

    data class Expanded(
        val numberToLearn: Int,
        val wayToLearn: Int = 0,
    ) : CustomLearnDialogState()
}
