package com.nestifff.words.domain.settings.usecase

import com.nestifff.words.domain.settings.SettingsRepository
import com.nestifff.words.domain.settings.model.SettingsDomain
import javax.inject.Inject

class GetSettingsUseCase @Inject constructor(
    private val repository: SettingsRepository
) {

    suspend fun execute(): SettingsDomain {
        return repository.getSettings()
    }
}
