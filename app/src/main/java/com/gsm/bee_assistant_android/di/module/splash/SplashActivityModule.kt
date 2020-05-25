package com.gsm.bee_assistant_android.di.module.splash

import com.gsm.bee_assistant_android.ui.SplashActivity
import com.gsm.bee_assistant_android.ui.contract.SplashContract
import com.gsm.bee_assistant_android.ui.presenter.SplashPresenter
import dagger.Binds
import dagger.Module

@Module
abstract class SplashActivityModule {

    @Binds
    abstract fun provideSplashPresenter(presenter : SplashPresenter) : SplashContract.Presenter

    @Binds
    abstract fun provideSplashView(view : SplashActivity) : SplashContract.View
}