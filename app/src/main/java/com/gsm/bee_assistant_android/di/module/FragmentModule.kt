package com.gsm.bee_assistant_android.di.module

import com.gsm.bee_assistant_android.di.module.fragment.*
import com.gsm.bee_assistant_android.di.scope.FragmentScope
import com.gsm.bee_assistant_android.ui.cafeteria.CafeteriaFragment
import com.gsm.bee_assistant_android.ui.calendar.CalendarFragment
import com.gsm.bee_assistant_android.ui.classroom.ClassroomFragment
import com.gsm.bee_assistant_android.ui.main.MainFragment
import com.gsm.bee_assistant_android.ui.setschool_dialog.SetSchoolDialogFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [SetSchoolDialogModule::class])
    abstract fun setSchoolDialogFragment(): SetSchoolDialogFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [CafeteriaModule::class])
    abstract fun cafeteriaFragment(): CafeteriaFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [CalendarModule::class])
    abstract fun calendarFragment(): CalendarFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [ClassroomModule::class])
    abstract fun classroomFragment(): ClassroomFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun mainFragment(): MainFragment
}