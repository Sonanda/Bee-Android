package com.gsm.bee_assistant_android.ui.calendar

import com.gsm.bee_assistant_android.base.BasePresenter
import com.gsm.bee_assistant_android.base.BaseView

class CalendarContract {
    interface View: BaseView<Presenter> {

        fun showSchedule(scheduleList: ArrayList<String>)
    }

    interface Presenter: BasePresenter<View> {

        fun getSchedule(year: Int, month: Int, day: Int)
    }
}