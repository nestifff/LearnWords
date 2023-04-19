package com.nestifff.learnwords.app.di

import com.nestifff.learnwords.presentation.screen.collection.di.CollectionComponent
import dagger.Module

@Module(subcomponents = [CollectionComponent::class])
class SubcomponentsModule
