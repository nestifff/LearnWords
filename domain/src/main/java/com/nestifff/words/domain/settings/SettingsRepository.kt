package com.nestifff.words.domain.settings

import com.nestifff.words.domain.learn.model.WayToLearnDomain

interface SettingsRepository {

    suspend fun getNumberToLearn(): Int

    suspend fun updateNumberToLearn(number: Int)

    suspend fun getWayToLearn(): WayToLearnDomain

    suspend fun updateWayToLearn(wayToLearnDomain: WayToLearnDomain)

    suspend fun getNumberOnFirstTryToMoveInLearned(): Int

    suspend fun updateNumberOnFirstTryToMoveInLearned(number: Int)

    companion object {
        const val DEFAULT_NUMBER_TO_LEARN = 10
        val DEFAULT_WAY_TO_LEARN = WayToLearnDomain.ENG_TO_RUS
        const val DEFAULT_NUMBER_ON_FIRST_TRY_TO_MOVE_TO_LEARNED = 3
    }
}