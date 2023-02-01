package com.nestifff.learnwords.app

import android.app.Application
import com.nestifff.learnwords.app.di.AppComponent
import com.nestifff.learnwords.app.di.DaggerAppComponent

class App: Application() {

    var appComponent: AppComponent = DaggerAppComponent.builder().application(this).build()

    override fun onCreate() {
        super.onCreate()
//        appComponentnent= DaggerAppComponent.builder().build()
    }
}
