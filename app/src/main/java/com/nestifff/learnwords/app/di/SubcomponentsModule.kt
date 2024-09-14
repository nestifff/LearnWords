package com.nestifff.learnwords.app.di

import com.nestifff.learnwords.presentation.screen.collection.di.CollectionScreenComponent
import com.nestifff.learnwords.presentation.screen.learn.di.LearnScreenComponent
import com.nestifff.learnwords.presentation.screen.settings.di.SettingsScreenComponent
import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Module

@AssistedModule
@Module(
    subcomponents = [
        CollectionScreenComponent::class,
        LearnScreenComponent::class,
        SettingsScreenComponent::class
    ],
    includes = [AssistedInject_SubcomponentsModule::class]
)
class SubcomponentsModule
