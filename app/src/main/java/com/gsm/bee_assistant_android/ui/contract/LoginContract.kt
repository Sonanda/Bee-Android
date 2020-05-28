package com.gsm.bee_assistant_android.ui.contract

import android.content.Intent
import com.gsm.bee_assistant_android.base.BasePresenter
import com.gsm.bee_assistant_android.base.BaseView

class LoginContract {

    interface View : BaseView<Presenter> {

        fun showLogin(signInIntent: Intent)

        fun finishActivity()
    }

    interface Presenter : BasePresenter<View> {

        fun googleLogin(requestCode: Int, resultCode: Int, data: Intent?)

        fun googleSignIn(signInIntent : Intent)
    }
}