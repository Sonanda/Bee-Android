package com.gsm.bee_assistant_android.ui.login.classroom

import com.gsm.bee_assistant_android.base.BasePresenter
import com.gsm.bee_assistant_android.base.BaseView
import com.gsm.bee_assistant_android.retrofit.domain.ClassroomToken

class ClassroomLoginContract {

    interface View : BaseView<Presenter> {

        fun onClickClassroomButton()

        fun onClickSkipButton()

        fun onClickCheckButton()

        fun showClassroomWebView(url: String)

        fun showProgress()

        fun hideProgress()

        fun finishActivity()

        fun changeVisibility(bool: Boolean)
    }

    interface Presenter : BasePresenter<View> {

        fun getClassroomUrl()

        fun getClassroomToken(token: String)

        fun setClassroomToken(classroomToken: ClassroomToken)
    }
}