package com.nestifff.learnwords.presentation.model

import com.nestifff.words.domain.learn.model.WayToLearnDomain

enum class WayToLearn {
    RusToEng, EngToRus,
}

fun WayToLearn.toDomain(): WayToLearnDomain {
    return when(this) {
        WayToLearn.RusToEng -> WayToLearnDomain.RUS_TO_ENG
        WayToLearn.EngToRus -> WayToLearnDomain.ENG_TO_RUS
    }
}

fun WayToLearnDomain.toUI(): WayToLearn {
    return when(this) {
        WayToLearnDomain.RUS_TO_ENG -> WayToLearn.RusToEng
        WayToLearnDomain.ENG_TO_RUS -> WayToLearn.EngToRus
    }
}
