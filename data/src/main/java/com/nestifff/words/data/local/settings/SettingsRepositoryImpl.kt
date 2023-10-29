package com.nestifff.words.data.local.settings

import com.nestifff.words.domain.learn.model.WayToLearnDomain
import com.nestifff.words.domain.settings.SettingsRepository
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor() : SettingsRepository {

    override suspend fun getNumberToLearn(): Int {
        return SettingsRepository.DEFAULT_NUMBER_TO_LEARN
    }

    override suspend fun updateNumberToLearn(number: Int) {

    }

    override suspend fun getWayToLearn(): WayToLearnDomain {
        return SettingsRepository.DEFAULT_WAY_TO_LEARN
    }

    override suspend fun updateWayToLearn(wayToLearnDomain: WayToLearnDomain) {

    }

    override suspend fun getNumberOnFirstTryToMoveInLearned(): Int {
        return SettingsRepository.DEFAULT_NUMBER_ON_FIRST_TRY_TO_MOVE_TO_LEARNED
    }

    override suspend fun updateNumberOnFirstTryToMoveInLearned(number: Int) {

    }
}