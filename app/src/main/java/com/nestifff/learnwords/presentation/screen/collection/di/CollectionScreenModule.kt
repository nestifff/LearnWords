package com.nestifff.learnwords.presentation.screen.collection.di

import com.nestifff.learnwords.presentation.screen.collection.CollectionViewModel
import com.nestifff.words.domain.collection.usecase.GetAllCollectionsFlowUseCase
import com.nestifff.words.domain.settings.usecase.GetLearnSettingsUseCase
import com.nestifff.words.domain.word.usecase.AddWordUseCase
import com.nestifff.words.domain.word.usecase.ChangeFavoritePropertyUseCase
import com.nestifff.words.domain.word.usecase.DeleteWordUseCase
import com.nestifff.words.domain.word.usecase.UpdateWordUseCase
import dagger.Module
import dagger.Provides


@Module
class CollectionScreenModule {

    @Provides
    @CollectionScreenScope
    fun provideViewModel(
        getAllCollectionsFlowUseCase: GetAllCollectionsFlowUseCase,
        updateWordUseCase: UpdateWordUseCase,
        addWordUseCase: AddWordUseCase,
        deleteWordUseCase: DeleteWordUseCase,
        changeFavoritePropertyUseCase: ChangeFavoritePropertyUseCase,
        getLearnSettingsUseCase: GetLearnSettingsUseCase,
    ): CollectionViewModel =
        CollectionViewModel(
            getAllCollectionsFlowUseCase = getAllCollectionsFlowUseCase,
            updateWordUseCase = updateWordUseCase,
            addWordUseCase = addWordUseCase,
            deleteWordUseCase = deleteWordUseCase,
            changeFavoritePropertyUseCase = changeFavoritePropertyUseCase,
            getLearnSettingsUseCase = getLearnSettingsUseCase,
        )
}
