package com.nestifff.words.domain.settings.model

import com.nestifff.words.domain.learn.model.WayToLearnDomain

data class SettingsDomain(
    val defaultNumberToLearn: Int,
    val defaultWayToLearn: WayToLearnDomain,
    val countOnFirstTryToMoveToLearned: Int,

    val isDarkMode: Boolean
)
