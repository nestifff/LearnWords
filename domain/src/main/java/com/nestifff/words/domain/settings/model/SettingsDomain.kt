package com.nestifff.words.domain.settings.model

import com.nestifff.words.domain.learn.model.WayToLearnDomain

data class SettingsDomain(
    val numberToLearn: Int,
    val wayToLearn: WayToLearnDomain,
    val numberOnFirstTryToMoveInLearned: Int,
)
