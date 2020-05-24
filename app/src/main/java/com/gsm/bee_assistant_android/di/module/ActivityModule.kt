package com.gsm.bee_assistant_android.di.module

import com.gsm.bee_assistant_android.di.module.main.MainActivityModule
import com.gsm.bee_assistant_android.di.scope.ActivityScope
import com.gsm.bee_assistant_android.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun mainActivity(): MainActivity
}