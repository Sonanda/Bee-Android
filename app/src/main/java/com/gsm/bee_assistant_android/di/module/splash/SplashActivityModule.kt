package com.gsm.bee_assistant_android.di.module.splash

import com.gsm.bee_assistant_android.ui.splash.SplashActivity
import com.gsm.bee_assistant_android.ui.splash.SplashContract
import com.gsm.bee_assistant_android.ui.splash.SplashPresenter
import dagger.Binds
import dagger.Module

@Module
abstract class SplashActivityModule {

    @Binds
    abstract fun provideSplashPresenter(presenter : SplashPresenter) : SplashContract.Presenter

    @Binds
    abstract fun provideSplashView(view : SplashActivity) : SplashContract.View
}