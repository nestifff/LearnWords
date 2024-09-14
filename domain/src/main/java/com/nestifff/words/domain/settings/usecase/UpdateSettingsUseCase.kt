package com.nestifff.words.domain.settings.usecase

import com.nestifff.words.domain.learn.model.WayToLearnDomain
import com.nestifff.words.domain.settings.SettingsRepository
import javax.inject.Inject

class UpdateSettingsUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend fun execute(
        defaultNumberToLearn: Int? = null,
        defaultWayToLearn: WayToLearnDomain? = null,
        countOnFirstTryToMoveToLearned: Int? = null,
        isDarkMode: Boolean? = null
    ) {
        repository.updateSettings(
            defaultNumberToLearn = defaultNumberToLearn,
            defaultWayToLearn = defaultWayToLearn,
            countOnFirstTryToMoveToLearned = countOnFirstTryToMoveToLearned,
            isDarkMode = isDarkMode
        )
    }
}
