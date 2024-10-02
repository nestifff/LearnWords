package com.nestifff.learnwords.presentation.screen.collection.model

import com.nestifff.learnwords.ext.emptyString

sealed class AddWordDialogState {

    data object Collapsed : AddWordDialogState()

    data class Expanded(
        val translation: String = emptyString(),
        val value: String = emptyString(),
    ) : AddWordDialogState()
}
