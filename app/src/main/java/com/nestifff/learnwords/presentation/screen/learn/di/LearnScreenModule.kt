package com.nestifff.learnwords.presentation.screen.learn.di

import com.nestifff.learnwords.presentation.screen.learn.LearnViewModel
import dagger.Module
import dagger.Provides


@Module
class LearnScreenModule {

    @Provides
    @LearnScreenScope
    fun provideViewModel(): LearnViewModel = LearnViewModel()
}
