package com.nestifff.words.data.di

import com.nestifff.words.data.local.database.repository.WordsRepositoryImpl
import com.nestifff.words.domain.interfaces.WordsRepository
import dagger.Binds
import dagger.Module

@Module
abstract class DomainBindsModule {
    @Binds
    abstract fun getWordsRepository(wordsRepositoryImpl: WordsRepositoryImpl): WordsRepository
}