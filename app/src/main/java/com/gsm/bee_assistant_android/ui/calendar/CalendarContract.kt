package com.gsm.bee_assistant_android.ui.calendar

import com.gsm.bee_assistant_android.base.BasePresenter
import com.gsm.bee_assistant_android.base.BaseView

class CalendarContract {
    interface View: BaseView<Presenter> {

    }

    interface Presenter: BasePresenter<View> {

    }
}