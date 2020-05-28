package com.gsm.bee_assistant_android.ui.contract

import com.gsm.bee_assistant_android.base.BasePresenter
import com.gsm.bee_assistant_android.base.BaseView

class SetSchoolContract {

    interface View : BaseView<Presenter>

    interface Presenter : BasePresenter<View> {

        val regionList: Array<String>
        val schoolKindList: Array<String>
        val schoolNameList: Array<String>

        fun getUserInfo()
    }
}