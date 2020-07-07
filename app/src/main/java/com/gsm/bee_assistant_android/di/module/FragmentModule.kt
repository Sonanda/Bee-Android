package com.gsm.bee_assistant_android.di.module

import com.gsm.bee_assistant_android.di.module.fragment.SetSchoolDialogModule
import com.gsm.bee_assistant_android.di.scope.FragmentScope
import com.gsm.bee_assistant_android.ui.setschool_dialog.SetSchoolDialogFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [SetSchoolDialogModule::class])
    abstract fun setSchoolDialogFragment(): SetSchoolDialogFragment
}