package com.nestifff.learnwords.presentation.screen.collection.di

import com.nestifff.learnwords.presentation.screen.collection.CollectionViewModel
import com.nestifff.learnwords.presentation.screen.collection.utils.WordsListCreator
import com.nestifff.learnwords.presentation.screen.settings.di.SettingsScreenScope
import com.nestifff.words.domain.usecase.*
import dagger.Module
import dagger.Provides


@Module
class CollectionModule {

    @Provides
    @CollectionScreenScope
    fun provideViewModel(
        getAllWordsFlowUseCase: GetAllWordsFlowUseCase,
        updateWordUseCase: UpdateWordUseCase,
        addWordUseCase: AddWordUseCase,
        deleteWordUseCase: DeleteWordUseCase
    ): CollectionViewModel =
        CollectionViewModel(
            getAllWordsFlowUseCase = getAllWordsFlowUseCase,
            updateWordUseCase = updateWordUseCase,
            addWordUseCase = addWordUseCase,
            deleteWordUseCase = deleteWordUseCase,
        )
}
