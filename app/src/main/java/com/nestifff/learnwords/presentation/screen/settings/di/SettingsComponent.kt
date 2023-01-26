package com.nestifff.learnwords.presentation.screen.settings.di

import com.nestifff.learnwords.presentation.screen.settings.SettingsViewModel
import dagger.Component


@Component(modules = [SettingsModule::class])
@SettingsScreenScope
interface SettingsComponent {

    @Component.Builder
    interface Builder {
        fun build(): SettingsComponent
    }

    fun getViewModel(): SettingsViewModel
}
