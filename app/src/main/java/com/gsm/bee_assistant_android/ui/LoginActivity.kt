package com.gsm.bee_assistant_android.ui

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.gsm.bee_assistant_android.R
import com.gsm.bee_assistant_android.base.BaseActivity
import com.gsm.bee_assistant_android.ui.contract.LoginContract
import javax.inject.Inject

class LoginActivity : BaseActivity(), LoginContract.View {

    @Inject
    override lateinit var presenter : LoginContract.Presenter

    override lateinit var binding: ViewDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    override fun init() {}

    override fun showKeyboard() {}

    override fun hideKeyboard() {}

    override fun showToast(message: String) {}

    override fun startActivity(activityName: Class<*>) {}
}
