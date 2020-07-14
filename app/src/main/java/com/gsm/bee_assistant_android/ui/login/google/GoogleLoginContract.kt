package com.gsm.bee_assistant_android.ui.login.google

import android.content.Intent
import com.gsm.bee_assistant_android.base.BasePresenter
import com.gsm.bee_assistant_android.base.BaseView

class GoogleLoginContract {

    interface View : BaseView<Presenter> {

        fun showLogin(signInIntent: Intent)

        fun finishActivity()

        fun onClickGoogleLoginButton()
    }

    interface Presenter : BasePresenter<View> {

        fun googleLogin(requestCode: Int, resultCode: Int, data: Intent?)

        fun googleSignIn(signInIntent : Intent)

        fun getUserInfo()

        fun checkUserInfoToChangeActivity()

        fun setSchoolInfo()
    }
}