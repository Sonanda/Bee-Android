package com.gsm.bee_assistant_android.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.gsm.bee_assistant_android.R
import com.gsm.bee_assistant_android.base.BaseActivity
import com.gsm.bee_assistant_android.databinding.ActivitySplashBinding
import com.gsm.bee_assistant_android.ui.contract.SplashContract
import javax.inject.Inject

class SplashActivity : BaseActivity(), SplashContract.View {

    @Inject
    override lateinit var presenter : SplashContract.Presenter

    override lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        binding.splash = this
    }

    override fun init() {}

    override fun showKeyboard() {}

    override fun hideKeyboard() {}

    override fun showToast(message: String) {}

    override fun startActivity(activityName: Class<*>) {}
}
