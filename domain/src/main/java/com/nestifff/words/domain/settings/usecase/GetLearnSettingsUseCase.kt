package com.nestifff.words.domain.settings.usecase

import com.nestifff.words.domain.settings.SettingsRepository
import com.nestifff.words.domain.settings.model.SettingsDomain
import javax.inject.Inject

class GetLearnSettingsUseCase @Inject constructor(
    private val repository: SettingsRepository
) {

    suspend fun run(): SettingsDomain {
        return SettingsDomain(
            numberToLearn = repository.getNumberToLearn(),
            wayToLearn = repository.getWayToLearn(),
            numberOnFirstTryToMoveInLearned = repository.getNumberOnFirstTryToMoveInLearned()
        )
    }
}
