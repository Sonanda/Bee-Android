package com.gsm.bee_assistant_android.di.module.main

import com.gsm.bee_assistant_android.ui.main.MainActivity
import com.gsm.bee_assistant_android.ui.main.MainContract
import com.gsm.bee_assistant_android.ui.main.MainPresenter
import dagger.Binds
import dagger.Module

@Module
abstract class MainActivityModule {

    @Binds
    abstract fun provideMainPresenter(presenter : MainPresenter): MainContract.Presenter

    @Binds
    abstract fun provideMainView(view : MainActivity): MainContract.View
}