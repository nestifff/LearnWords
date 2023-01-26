package com.nestifff.learnwords.presentation.screen.settings.di

import com.nestifff.learnwords.presentation.screen.settings.SettingsViewModel
import dagger.Module
import dagger.Provides


@Module
class SettingsModule {

    @Provides
    @SettingsScreenScope
    fun provideViewModel(): SettingsViewModel = SettingsViewModel()
}
