package com.nestifff.words.data.di

import com.nestifff.words.data.local.database.repository.LearnRepositoryImpl
import com.nestifff.words.data.local.database.repository.WordsRepositoryImpl
import com.nestifff.words.data.local.settings.SettingsRepositoryImpl
import com.nestifff.words.domain.learn.LearnRepository
import com.nestifff.words.domain.settings.SettingsRepository
import com.nestifff.words.domain.word.WordsRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class DomainBindsModule {

    @Binds
    abstract fun getWordsRepository(wordsRepositoryImpl: WordsRepositoryImpl): WordsRepository

    @Binds
    @Singleton
    abstract fun getLearnRepository(learnRepositoryImpl: LearnRepositoryImpl): LearnRepository

    @Binds
    @Singleton
    abstract fun getSettingsRepository(settingsRepositoryImpl: SettingsRepositoryImpl): SettingsRepository

}
