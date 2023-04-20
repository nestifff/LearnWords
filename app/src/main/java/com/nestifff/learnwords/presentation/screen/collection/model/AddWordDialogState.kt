package com.nestifff.learnwords.presentation.screen.collection.model

import com.nestifff.learnwords.ext.emptyString

sealed class AddWordDialogState {

    object Hidden : AddWordDialogState()

    data class Expanded(
        val rus: String,
        val eng: String,
    ) : AddWordDialogState() {

        companion object {
            fun empty() = Expanded(emptyString(), emptyString())
        }
    }
}
