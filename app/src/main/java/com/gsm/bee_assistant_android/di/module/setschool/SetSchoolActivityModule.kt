package com.gsm.bee_assistant_android.di.module.setschool

import com.gsm.bee_assistant_android.ui.setschool.SetSchoolActivity
import com.gsm.bee_assistant_android.ui.setschool.SetSchoolContract
import com.gsm.bee_assistant_android.ui.setschool.SetSchoolPresenter
import dagger.Binds
import dagger.Module

@Module
abstract class SetSchoolActivityModule {

    @Binds
    abstract fun provideSetSchoolPresenter(presenter : SetSchoolPresenter) : SetSchoolContract.Presenter

    @Binds
    abstract fun provideSetSchoolView(view : SetSchoolActivity) : SetSchoolContract.View
}