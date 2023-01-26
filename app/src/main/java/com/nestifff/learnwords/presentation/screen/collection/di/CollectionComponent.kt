package com.nestifff.learnwords.presentation.screen.collection.di

import com.nestifff.learnwords.presentation.screen.collection.CollectionViewModel
import com.nestifff.learnwords.presentation.screen.settings.di.SettingsComponent
import com.nestifff.learnwords.presentation.screen.settings.di.SettingsModule
import com.nestifff.learnwords.presentation.screen.settings.di.SettingsScreenScope
import dagger.Component


@Component(modules = [CollectionModule::class])
@CollectionScreenScope
interface CollectionComponent {

    @Component.Builder
    interface Builder {
        fun build(): CollectionComponent
    }

    fun getViewModel() : CollectionViewModel
}
