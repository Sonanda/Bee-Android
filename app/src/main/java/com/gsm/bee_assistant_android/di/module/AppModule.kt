package com.gsm.bee_assistant_android.di.module

import android.app.Application
import android.content.Context
import com.gsm.bee_assistant_android.di.app.MyApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun provideContext(application: MyApplication): Context = application

    @Provides
    @Singleton
    fun provideApplication(application: MyApplication): Application = application
}