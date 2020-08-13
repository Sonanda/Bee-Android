package com.gsm.bee_assistant_android.di.module.fragment

import com.gsm.bee_assistant_android.ui.calendar.CalendarContract
import com.gsm.bee_assistant_android.ui.calendar.CalendarFragment
import com.gsm.bee_assistant_android.ui.calendar.CalendarPresenter
import dagger.Binds
import dagger.Module

@Module
abstract class CalendarModule {

    @Binds
    abstract fun provideCalendarPresenter(presenter : CalendarPresenter): CalendarContract.Presenter

    @Binds
    abstract fun provideCalendarView(view : CalendarFragment): CalendarContract.View
}