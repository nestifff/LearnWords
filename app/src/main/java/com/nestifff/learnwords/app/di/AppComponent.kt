package com.nestifff.learnwords.app.di

import android.app.Application
import com.nestifff.learnwords.app.App
import com.nestifff.learnwords.presentation.screen.root.MainActivity
import com.nestifff.learnwords.presentation.screen.root.MainActivityModule
import com.nestifff.words.data.di.DatabaseModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MainActivityModule::class, DatabaseModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)

    fun inject(mainActivity: MainActivity)
}
