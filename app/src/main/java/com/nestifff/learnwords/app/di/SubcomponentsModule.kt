package com.nestifff.learnwords.app.di

import com.nestifff.learnwords.presentation.screen.collection.di.CollectionScreenComponent
import com.nestifff.learnwords.presentation.screen.learn.di.LearnScreenComponent
import dagger.Module

@Module(subcomponents = [CollectionScreenComponent::class, LearnScreenComponent::class])
class SubcomponentsModule
