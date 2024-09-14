package com.nestifff.learnwords.presentation.screen.settings.di

import com.nestifff.learnwords.presentation.screen.result.di.ResultScreenComponent
import com.nestifff.learnwords.presentation.screen.settings.SettingsViewModel
import dagger.Component
import dagger.Subcomponent


@Subcomponent(modules = [SettingsModule::class])
@SettingsScreenScope
interface SettingsScreenComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): SettingsScreenComponent
    }

    fun getViewModel(): SettingsViewModel
}
