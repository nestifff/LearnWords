package com.nestifff.learnwords.presentation.screen.collection.di

import com.nestifff.learnwords.presentation.screen.collection.CollectionViewModel
import dagger.Subcomponent


@CollectionScreenScope
@Subcomponent(modules = [CollectionScreenModule::class])
interface CollectionScreenComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): CollectionScreenComponent
    }

    fun getViewModel() : CollectionViewModel
}
