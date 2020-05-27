package com.gsm.bee_assistant_android.ui.contract

import com.gsm.bee_assistant_android.base.BasePresenter
import com.gsm.bee_assistant_android.base.BaseView

class SplashContract {

    interface View : BaseView<Presenter> {

        fun finishActivity()
    }

    interface Presenter : BasePresenter<View> {

        fun checkUserInfo()
    }
}