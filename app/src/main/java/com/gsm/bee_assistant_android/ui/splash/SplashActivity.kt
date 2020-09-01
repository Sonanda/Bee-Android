package com.gsm.bee_assistant_android.ui.splash

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.gsm.bee_assistant_android.R
import com.gsm.bee_assistant_android.base.BaseActivity
import com.gsm.bee_assistant_android.databinding.ActivitySplashBinding
import dagger.android.AndroidInjection
import javax.inject.Inject

class SplashActivity : BaseActivity(), SplashContract.View {

    @Inject
    override lateinit var presenter : SplashContract.Presenter

    override lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidInjection.inject(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        binding.splash = this

        init()
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter.disposeDisposable()
    }

    override fun init() = presenter.checkUserInfo()

    override fun showProgress() {}

    override fun hideProgress() {}

    override fun showToast(message: String)  = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    override fun startActivity(activityName: Class<*>) { startActivity(Intent(this, activityName)) }

    override fun finishActivity() = finish()
}
