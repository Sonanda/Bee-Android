package com.gsm.bee_assistant_android.ui.contract

import com.gsm.bee_assistant_android.base.BasePresenter
import com.gsm.bee_assistant_android.base.BaseView

class LoginContract {

    interface View : BaseView<Presenter> {
    }

    interface Presenter : BasePresenter<View> {
    }
}