package com.chen.mygooglemap.modules

import com.chen.mygooglemap.ui.GMapFragment
import com.chen.mygooglemap.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Module for activity and fragment injection
 */
@Suppress("unused")
@Module
interface UIModule {

    @ContributesAndroidInjector
    fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    fun contributeGMapFragment(): GMapFragment
}