package com.chen.mygooglemap.modules

import android.content.Context
import com.chen.mygooglemap.Application
import dagger.Module
import dagger.Provides

/**
 * Injection for Application
 */
@Module
class AppModule {

    @Provides
    fun providesAppContext(app: Application): Context {
        return app
    }

}