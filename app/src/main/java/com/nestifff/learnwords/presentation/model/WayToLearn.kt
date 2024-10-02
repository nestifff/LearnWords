package com.nestifff.learnwords.presentation.model

import com.nestifff.words.domain.learn.model.WayToLearnDomain

enum class WayToLearn {
    WriteLearningValue, WriteTranslation,
}

fun WayToLearn.toDomain(): WayToLearnDomain {
    return when(this) {
        WayToLearn.WriteLearningValue -> WayToLearnDomain.WRITE_LEARNING_VALUE
        WayToLearn.WriteTranslation -> WayToLearnDomain.WRITE_TRANSLATION
    }
}

fun WayToLearnDomain.toUI(): WayToLearn {
    return when(this) {
        WayToLearnDomain.WRITE_LEARNING_VALUE -> WayToLearn.WriteLearningValue
        WayToLearnDomain.WRITE_TRANSLATION -> WayToLearn.WriteTranslation
    }
}
