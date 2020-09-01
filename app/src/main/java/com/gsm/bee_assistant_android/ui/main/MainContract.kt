package com.gsm.bee_assistant_android.ui.main

import com.gsm.bee_assistant_android.base.BasePresenter
import com.gsm.bee_assistant_android.base.BaseView

class MainContract {

    interface View : BaseView<Presenter> {

        fun finishAffinityActivity()

        fun finishActivity()
    }

    interface Presenter : BasePresenter<View> {

        fun backPressed()

        fun getUserEmail(): String

        fun getSchoolName(): String

        fun logout()
    }
}