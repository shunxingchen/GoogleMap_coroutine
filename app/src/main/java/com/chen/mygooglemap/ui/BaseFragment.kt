package com.chen.mygooglemap.ui

import dagger.android.support.DaggerFragment

abstract class BaseFragment<T : BaseActivity> : DaggerFragment() {

    @Suppress("UNCHECKED_CAST")
    val activity: T get() = getActivity() as T

}