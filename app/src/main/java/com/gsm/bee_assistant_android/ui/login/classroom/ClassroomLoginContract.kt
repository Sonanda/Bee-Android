package com.gsm.bee_assistant_android.ui.login.classroom

import com.gsm.bee_assistant_android.base.BasePresenter
import com.gsm.bee_assistant_android.base.BaseView

class ClassroomLoginContract {

    interface View : BaseView<Presenter> {

        fun onClickClassroomButton()

        fun onClickSkipButton()

        fun showClassroomWebView(url: String)

        fun finishActivity()
    }

    interface Presenter : BasePresenter<View> {

        fun getClassroomUrl()
    }
}