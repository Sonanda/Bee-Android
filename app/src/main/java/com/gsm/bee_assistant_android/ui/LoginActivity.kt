package com.gsm.bee_assistant_android.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.gsm.bee_assistant_android.R
import com.gsm.bee_assistant_android.base.BaseActivity
import com.gsm.bee_assistant_android.databinding.ActivityLoginBinding
import com.gsm.bee_assistant_android.ui.contract.LoginContract
import javax.inject.Inject

class LoginActivity : BaseActivity(), LoginContract.View {

    @Inject
    override lateinit var presenter : LoginContract.Presenter

    override lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.login = this
    }

    override fun init() {}

    override fun showKeyboard() {}

    override fun hideKeyboard() {}

    override fun showToast(message: String) {}

    override fun startActivity(activityName: Class<*>) {}
}
