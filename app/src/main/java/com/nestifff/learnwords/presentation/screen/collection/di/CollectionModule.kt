package com.nestifff.learnwords.presentation.screen.collection.di

import com.nestifff.learnwords.presentation.screen.collection.CollectionViewModel
import com.nestifff.learnwords.presentation.screen.collection.utils.WordsListCreator
import com.nestifff.learnwords.presentation.screen.settings.di.SettingsScreenScope
import dagger.Module
import dagger.Provides


@Module
class CollectionModule {

    @Provides
    @CollectionScreenScope
    fun provideViewModel(wordsListCreator: WordsListCreator): CollectionViewModel =
        CollectionViewModel(wordsListCreator = wordsListCreator)
}
