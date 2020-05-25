package com.gsm.bee_assistant_android.di.module.setschool

import com.gsm.bee_assistant_android.ui.SetSchoolActivity
import com.gsm.bee_assistant_android.ui.contract.SetSchoolContract
import com.gsm.bee_assistant_android.ui.presenter.SetSchoolPresenter
import dagger.Binds
import dagger.Module

@Module
abstract class SetSchoolActivityModule {

    @Binds
    abstract fun provideSetSchoolPresenter(presenter : SetSchoolPresenter) : SetSchoolContract.Presenter

    @Binds
    abstract fun provideSetSchoolView(view : SetSchoolActivity) : SetSchoolContract.View
}