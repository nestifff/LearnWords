package com.nestifff.learnwords.presentation.screen.result.di

import com.nestifff.learnwords.presentation.screen.result.ResultViewModel
import com.nestifff.words.domain.learn.usecase.GetLearnProcessResultUseCase
import dagger.Module
import dagger.Provides


@Module
class ResultScreenModule {

    @Provides
    @ResultScreenScope
    fun provideViewModel(
        getLearnProcessResultUseCase: GetLearnProcessResultUseCase
    ): ResultViewModel =
        ResultViewModel(
            getLearnProcessResultUseCase = getLearnProcessResultUseCase
        )
}
