package com.nestifff.learnwords.presentation.screen.root

import dagger.Module

@Module
abstract class MainActivityModule {

    abstract fun inject(mainActivityModule: MainActivityModule)
}
