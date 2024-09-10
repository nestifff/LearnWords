package com.nestifff.learnwords.presentation.screen.collection.model

import com.nestifff.learnwords.ext.emptyString

data class AddWordDialogState(
    val rus: String = emptyString(),
    val eng: String = emptyString(),
)