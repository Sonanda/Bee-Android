package com.gsm.bee_assistant_android.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.gsm.bee_assistant_android.R
import com.gsm.bee_assistant_android.base.BaseActivity
import com.gsm.bee_assistant_android.databinding.ActivityMainBinding
import com.gsm.bee_assistant_android.ui.contract.MainContract
import javax.inject.Inject

class MainActivity : BaseActivity(), MainContract.View {

    @Inject
    override lateinit var presenter : MainContract.Presenter

    override lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.main = this
    }

    override fun init() {}

    override fun showKeyboard() {}

    override fun hideKeyboard() {}

    override fun showToast(message: String) {}

    override fun startActivity(activityName: Class<*>) {}
}
