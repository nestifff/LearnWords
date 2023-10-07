package com.nestifff.learnwords.app.di

import android.app.Application
import com.nestifff.learnwords.app.App
import com.nestifff.learnwords.presentation.screen.collection.di.CollectionScreenComponent
import com.nestifff.learnwords.presentation.screen.learn.di.LearnScreenComponent
import com.nestifff.learnwords.presentation.screen.root.MainActivity
import com.nestifff.learnwords.presentation.screen.root.MainActivityModule
import com.nestifff.words.data.di.DatabaseModule
import com.nestifff.words.data.di.DomainBindsModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        SubcomponentsModule::class,
        MainActivityModule::class,
        DatabaseModule::class,
        DomainBindsModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)

    // TODO move to MainActivityModule
    fun inject(mainActivity: MainActivity)

    // TODO try implement
    fun collectionScreenComponent(): CollectionScreenComponent.Factory

    fun learnScreenComponent(): LearnScreenComponent.Factory
}
