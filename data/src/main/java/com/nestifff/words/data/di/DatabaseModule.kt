package com.nestifff.words.data.di

import android.app.Application
import androidx.room.Room
import com.nestifff.words.data.local.database.AppDatabase
import com.nestifff.words.data.local.database.dao.WordsDatabaseDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): AppDatabase =
        Room
            .databaseBuilder(app, AppDatabase::class.java, "words.db")
            .build()

    @Singleton
    @Provides
    fun provideUserDao(db: AppDatabase): WordsDatabaseDao {
        return db.wordsDao()
    }
}
