package com.nestifff.words.domain.settings.usecase

import com.nestifff.words.domain.settings.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveIsDarkModeUseCase @Inject constructor(
    private val repository: SettingsRepository
) {

    suspend fun execute(): Flow<Boolean> {
        return repository.observeDarkMode()
    }
}