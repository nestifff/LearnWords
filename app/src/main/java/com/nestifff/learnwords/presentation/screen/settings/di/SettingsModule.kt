package com.nestifff.learnwords.presentation.screen.settings.di

import com.nestifff.learnwords.presentation.screen.settings.SettingsViewModel
import com.nestifff.words.domain.settings.usecase.GetSettingsUseCase
import com.nestifff.words.domain.settings.usecase.UpdateSettingsUseCase
import dagger.Module
import dagger.Provides


@Module
class SettingsModule {

    @Provides
    @SettingsScreenScope
    fun provideViewModel(
        getSettingsUseCase: GetSettingsUseCase,
        updateSettingsUseCase: UpdateSettingsUseCase
    ): SettingsViewModel = SettingsViewModel(
        getSettingsUseCase = getSettingsUseCase,
        updateSettingsUseCase = updateSettingsUseCase
    )
}
