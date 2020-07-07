package com.gsm.bee_assistant_android.di.module.login

import com.gsm.bee_assistant_android.ui.login.LoginActivity
import com.gsm.bee_assistant_android.ui.login.LoginContract
import com.gsm.bee_assistant_android.ui.login.LoginPresenter
import dagger.Binds
import dagger.Module

@Module
abstract class LoginActivityModule {

    @Binds
    abstract fun provideLoginPresenter(presenter : LoginPresenter) : LoginContract.Presenter

    @Binds
    abstract fun provideLoginView(view : LoginActivity) : LoginContract.View
}