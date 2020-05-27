package com.gsm.bee_assistant_android.ui.contract

import android.content.Context
import com.gsm.bee_assistant_android.base.BasePresenter
import com.gsm.bee_assistant_android.base.BaseView

class SetSchoolContract {

    interface View : BaseView<Presenter> {

        fun getContext(): Context
    }

    interface Presenter : BasePresenter<View> {

        val context: Context

        val regionList: Array<String>
        val schoolKindList: Array<String>
        val schoolNameList: Array<String>

        fun getUserInfo()
    }
}