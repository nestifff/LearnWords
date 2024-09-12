package com.nestifff.words.domain.settings.usecase

import com.nestifff.words.domain.settings.SettingsRepository
import com.nestifff.words.domain.settings.model.SettingsDomain
import javax.inject.Inject

class GetLearnSettingsUseCase @Inject constructor(
    private val repository: SettingsRepository
) {

    suspend fun execute(): SettingsDomain {
        return SettingsDomain(
            numberToLearn = repository.getCountToLearn(),
            wayToLearn = repository.getWayToLearn(),
            numberOnFirstTryToMoveInLearned = repository.getNumberOnFirstTryToMoveInLearned()
        )
    }
}
