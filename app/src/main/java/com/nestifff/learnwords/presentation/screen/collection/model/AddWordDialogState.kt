package com.nestifff.learnwords.presentation.screen.collection.model

import com.nestifff.learnwords.ext.emptyString

sealed class AddWordDialogState {

    data object Hidden : AddWordDialogState()

    data class Expanded(
        val rus: String = emptyString(),
        val eng: String = emptyString(),
    ) : AddWordDialogState()
}
