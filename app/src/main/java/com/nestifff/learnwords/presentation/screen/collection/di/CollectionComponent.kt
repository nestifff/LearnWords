package com.nestifff.learnwords.presentation.screen.collection.di

import com.nestifff.learnwords.presentation.screen.collection.CollectionViewModel
import com.nestifff.learnwords.presentation.screen.settings.di.SettingsComponent
import com.nestifff.learnwords.presentation.screen.settings.di.SettingsModule
import com.nestifff.learnwords.presentation.screen.settings.di.SettingsScreenScope
import dagger.Component
import dagger.Subcomponent


@CollectionScreenScope
@Subcomponent(modules = [CollectionModule::class])
interface CollectionComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): CollectionComponent
    }

    fun getViewModel() : CollectionViewModel
}
