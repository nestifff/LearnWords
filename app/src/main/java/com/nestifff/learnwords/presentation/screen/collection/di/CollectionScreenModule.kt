package com.nestifff.learnwords.presentation.screen.collection.di

import com.nestifff.learnwords.presentation.screen.collection.CollectionViewModel
import com.nestifff.words.domain.word.usecase.AddWordUseCase
import com.nestifff.words.domain.word.usecase.ChangeFavoritePropertyUseCase
import com.nestifff.words.domain.word.usecase.DeleteWordUseCase
import com.nestifff.words.domain.usecase.word.GetAllWordsFlowUseCase
import com.nestifff.words.domain.word.usecase.UpdateWordUseCase
import dagger.Module
import dagger.Provides


@Module
class CollectionScreenModule {

    @Provides
    @CollectionScreenScope
    fun provideViewModel(
        getAllWordsFlowUseCase: GetAllWordsFlowUseCase,
        updateWordUseCase: UpdateWordUseCase,
        addWordUseCase: AddWordUseCase,
        deleteWordUseCase: DeleteWordUseCase,
        changeFavoritePropertyUseCase: ChangeFavoritePropertyUseCase,
    ): CollectionViewModel =
        CollectionViewModel(
            getAllWordsFlowUseCase = getAllWordsFlowUseCase,
            updateWordUseCase = updateWordUseCase,
            addWordUseCase = addWordUseCase,
            deleteWordUseCase = deleteWordUseCase,
            changeFavoritePropertyUseCase = changeFavoritePropertyUseCase,
        )
}
