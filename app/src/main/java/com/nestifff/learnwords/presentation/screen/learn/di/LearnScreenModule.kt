package com.nestifff.learnwords.presentation.screen.learn.di

import com.nestifff.learnwords.presentation.screen.collection.CollectionViewModel
import com.nestifff.words.domain.usecase.AddWordUseCase
import com.nestifff.words.domain.usecase.DeleteWordUseCase
import com.nestifff.words.domain.usecase.GetAllWordsFlowUseCase
import com.nestifff.words.domain.usecase.UpdateWordUseCase
import dagger.Module
import dagger.Provides


@Module
class LearnScreenModule {

    @Provides
    @LearnScreenScope
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
