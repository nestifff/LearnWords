package com.nestifff.learnwords.presentation.screen.learn.di

import dagger.Subcomponent


@LearnScreenScope
@Subcomponent
interface LearnScreenComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): LearnScreenComponent
    }

    val viewModelFactory: LearnViewModelFactory
}
