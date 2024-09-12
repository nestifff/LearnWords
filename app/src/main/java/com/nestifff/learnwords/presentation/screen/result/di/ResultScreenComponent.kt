package com.nestifff.learnwords.presentation.screen.result.di

import com.nestifff.learnwords.presentation.screen.result.ResultViewModel
import dagger.Subcomponent

@ResultScreenScope
@Subcomponent(modules = [ResultScreenModule::class])
interface ResultScreenComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): ResultScreenComponent
    }

    fun getViewModel() : ResultViewModel
}
