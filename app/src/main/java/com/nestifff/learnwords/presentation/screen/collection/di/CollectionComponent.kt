package com.nestifff.learnwords.presentation.screen.collection.di

import com.nestifff.learnwords.presentation.screen.collection.CollectionViewModel
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
