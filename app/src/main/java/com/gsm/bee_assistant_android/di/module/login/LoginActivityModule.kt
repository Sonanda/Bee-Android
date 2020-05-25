package com.gsm.bee_assistant_android.di.module.login

import com.gsm.bee_assistant_android.ui.LoginActivity
import com.gsm.bee_assistant_android.ui.contract.LoginContract
import com.gsm.bee_assistant_android.ui.presenter.LoginPresenter
import dagger.Binds
import dagger.Module

@Module
abstract class LoginActivityModule {

    @Binds
    abstract fun provideLoginPresenter(presenter : LoginPresenter) : LoginContract.Presenter

    @Binds
    abstract fun provideLoginView(view : LoginActivity) : LoginContract.View
}