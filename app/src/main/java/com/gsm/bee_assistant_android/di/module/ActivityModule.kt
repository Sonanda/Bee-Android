package com.gsm.bee_assistant_android.di.module

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.gsm.bee_assistant_android.di.module.login.ClassroomLoginActivityModule
import com.gsm.bee_assistant_android.di.module.login.GoogleLoginActivityModule
import com.gsm.bee_assistant_android.di.module.main.MainActivityModule
import com.gsm.bee_assistant_android.di.module.setschool.SetSchoolActivityModule
import com.gsm.bee_assistant_android.di.module.splash.SplashActivityModule
import com.gsm.bee_assistant_android.di.scope.ActivityScope
import com.gsm.bee_assistant_android.ui.login.classroom.ClassroomLoginActivity
import com.gsm.bee_assistant_android.ui.login.google.GoogleLoginActivity
import com.gsm.bee_assistant_android.ui.main.MainActivity
import com.gsm.bee_assistant_android.ui.setschool.SetSchoolActivity
import com.gsm.bee_assistant_android.ui.splash.SplashActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [SplashActivityModule::class])
    abstract fun splashActivity(): SplashActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [GoogleLoginActivityModule::class])
    abstract fun googleLoginActivity(): GoogleLoginActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [ClassroomLoginActivityModule::class])
    abstract fun classroomLoginActivity(): ClassroomLoginActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [SetSchoolActivityModule::class])
    abstract fun setSchoolActivity(): SetSchoolActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun mainActivity(): MainActivity
}