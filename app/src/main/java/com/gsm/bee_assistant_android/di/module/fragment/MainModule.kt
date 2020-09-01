package com.gsm.bee_assistant_android.di.module.fragment

import com.gsm.bee_assistant_android.ui.main.MainContract
import com.gsm.bee_assistant_android.ui.main.MainFragment
import com.gsm.bee_assistant_android.ui.main.MainPresenter
import dagger.Binds
import dagger.Module

@Module
abstract class MainModule {

    @Binds
    abstract fun provideMainPresenter(presenter : MainPresenter): MainContract.Presenter

    @Binds
    abstract fun provideMainFragment(view : MainFragment): MainContract.View
}