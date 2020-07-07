package com.gsm.bee_assistant_android.di.module.fragment

import com.gsm.bee_assistant_android.ui.setschool_dialog.SetSchoolDialogFragment
import com.gsm.bee_assistant_android.ui.setschool_dialog.SetSchoolDialogContract
import com.gsm.bee_assistant_android.ui.setschool_dialog.SetSchoolDialogPresenter
import dagger.Binds
import dagger.Module

@Module
abstract class SetSchoolDialogModule {

    @Binds
    abstract fun provideSetSchoolDialogPresenter(presenter : SetSchoolDialogPresenter): SetSchoolDialogContract.Presenter

    @Binds
    abstract fun provideSetSchoolDialogView(view : SetSchoolDialogFragment): SetSchoolDialogContract.View
}