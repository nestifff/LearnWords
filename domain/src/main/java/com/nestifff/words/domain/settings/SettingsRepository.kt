package com.nestifff.words.domain.settings

import com.nestifff.words.domain.learn.model.WayToLearnDomain
import com.nestifff.words.domain.settings.model.SettingsDomain

interface SettingsRepository {

    suspend fun getSettings(): SettingsDomain

    suspend fun updateSettings(
        defaultNumberToLearn: Int? = null,
        defaultWayToLearn: WayToLearnDomain? = null,
        countOnFirstTryToMoveToLearned: Int? = null,
        isDarkMode: Boolean? = null
    )

    companion object {
        const val INITIAL_NUMBER_TO_LEARN = 3
        val INITIAL_WAY_TO_LEARN = WayToLearnDomain.ENG_TO_RUS
        const val INITIAL_NUMBER_ON_FIRST_TRY = 1
        const val INITIAL_IS_DARK_MODE = false
    }
}
