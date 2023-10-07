package com.nestifff.learnwords.presentation.screen.learn.di

import com.nestifff.learnwords.presentation.screen.learn.LearnViewModel
import dagger.Subcomponent


@LearnScreenScope
@Subcomponent(modules = [LearnScreenModule::class])
interface LearnScreenComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): LearnScreenComponent
    }

    fun getViewModel() : LearnViewModel
}
