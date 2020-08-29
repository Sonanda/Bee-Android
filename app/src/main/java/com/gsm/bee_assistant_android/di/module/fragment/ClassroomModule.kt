package com.gsm.bee_assistant_android.di.module.fragment

import com.gsm.bee_assistant_android.ui.classroom.ClassroomContract
import com.gsm.bee_assistant_android.ui.classroom.ClassroomFragment
import com.gsm.bee_assistant_android.ui.classroom.ClassroomPresenter
import dagger.Binds
import dagger.Module

@Module
abstract class ClassroomModule {

    @Binds
    abstract fun provideClassroomPresenter(presenter : ClassroomPresenter): ClassroomContract.Presenter

    @Binds
    abstract fun provideClassroomFragment(view : ClassroomFragment): ClassroomContract.View
}