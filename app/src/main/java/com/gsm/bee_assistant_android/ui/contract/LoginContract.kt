package com.gsm.bee_assistant_android.ui.contract

import android.content.Context
import android.content.Intent
import com.gsm.bee_assistant_android.base.BasePresenter
import com.gsm.bee_assistant_android.base.BaseView

class LoginContract {

    interface View : BaseView<Presenter> {

        fun getContext(): Context

        fun showLogin(signInIntent: Intent)
    }

    interface Presenter : BasePresenter<View> {

        val context: Context

        fun googleLogin(requestCode: Int, resultCode: Int, data: Intent?)

        fun googleSignIn(signInIntent : Intent)

        fun getUserInfo()
    }
}