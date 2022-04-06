package com.chen.mygooglemap

import com.chen.mygooglemap.data.DataModule
import com.chen.mygooglemap.data.remote.NetworkModule
import com.chen.mygooglemap.modules.AppModule
import com.chen.mygooglemap.modules.UIModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

/**
 * Component providing inject() modules for presenters.
 */
@Singleton
@Component(modules = [AndroidInjectionModule::class, AppModule::class, UIModule::class, DataModule::class, NetworkModule::class])
interface ApplicationComponent : AndroidInjector<Application> {

    @Component.Builder
    interface Builder {
        fun build(): ApplicationComponent

        @BindsInstance
        fun application(app: Application): Builder
    }
}