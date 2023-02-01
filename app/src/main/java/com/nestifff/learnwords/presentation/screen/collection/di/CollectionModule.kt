package com.nestifff.learnwords.presentation.screen.collection.di

import com.nestifff.learnwords.presentation.screen.collection.CollectionViewModel
import com.nestifff.learnwords.presentation.screen.collection.utils.WordsListCreator
import com.nestifff.learnwords.presentation.screen.settings.di.SettingsScreenScope
import com.nestifff.words.domain.usecase.AddWordUseCase
import com.nestifff.words.domain.usecase.GetAllWordsFlowUseCase
import com.nestifff.words.domain.usecase.GetAllWordsUseCase
import dagger.Module
import dagger.Provides


@Module
class CollectionModule {

    @Provides
    @CollectionScreenScope
    fun provideViewModel(
        getAllWordsFlowUseCase: GetAllWordsFlowUseCase,
        getAllWordsUseCase: GetAllWordsUseCase,
        addWordUseCase: AddWordUseCase,
    ): CollectionViewModel =
        CollectionViewModel(
            getAllWordsFlowUseCase = getAllWordsFlowUseCase,
            getAllWordsUseCase = getAllWordsUseCase,
            addWordUseCase = addWordUseCase
        )
}
