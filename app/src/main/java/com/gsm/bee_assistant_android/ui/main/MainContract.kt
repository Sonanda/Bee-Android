package com.gsm.bee_assistant_android.ui.main

import com.gsm.bee_assistant_android.base.BasePresenter
import com.gsm.bee_assistant_android.base.BaseView

class MainContract {

    interface View : BaseView<Presenter>, android.view.View.OnClickListener {

        fun finishAffinityActivity()

        fun finishActivity()

        fun onClickLogoutButton()

        fun onClickChangeSchoolButton()
    }

    interface Presenter : BasePresenter<View> {

        fun backPressed()

        fun getUserEmail(): String

        fun getSchoolName(): String

        fun logout()

        fun changeSchool(schoolName: String)
    }
}