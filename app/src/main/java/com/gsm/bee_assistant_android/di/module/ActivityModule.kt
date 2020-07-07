package com.gsm.bee_assistant_android.di.module

import com.gsm.bee_assistant_android.di.module.login.LoginActivityModule
import com.gsm.bee_assistant_android.di.module.main.MainActivityModule
import com.gsm.bee_assistant_android.di.module.setschool.SetSchoolActivityModule
import com.gsm.bee_assistant_android.di.module.splash.SplashActivityModule
import com.gsm.bee_assistant_android.di.scope.ActivityScope
import com.gsm.bee_assistant_android.ui.login.LoginActivity
import com.gsm.bee_assistant_android.ui.main.MainActivity
import com.gsm.bee_assistant_android.ui.setschool.SetSchoolActivity
import com.gsm.bee_assistant_android.ui.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [SplashActivityModule::class])
    abstract fun splashActivity(): SplashActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [LoginActivityModule::class])
    abstract fun loginActivity(): LoginActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [SetSchoolActivityModule::class])
    abstract fun setSchoolActivity(): SetSchoolActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun mainActivity(): MainActivity
}