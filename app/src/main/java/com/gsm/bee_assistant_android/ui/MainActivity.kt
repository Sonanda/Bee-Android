package com.gsm.bee_assistant_android.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.gsm.bee_assistant_android.R
import com.gsm.bee_assistant_android.base.BaseActivity
import com.gsm.bee_assistant_android.databinding.ActivityMainBinding
import com.gsm.bee_assistant_android.ui.contract.MainContract
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : BaseActivity(), MainContract.View {

    @Inject
    override lateinit var presenter : MainContract.Presenter

    override lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.main = this

        AndroidInjection.inject(this)
    }


    override fun onDestroy() {
        super.onDestroy()
        presenter.disposeDisposable()
    }

    override fun onBackPressed() = presenter.backPressed()

    override fun init() {}

    override fun showKeyboard() {}

    override fun hideKeyboard() {}

    override fun showToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    override fun startActivity(activityName: Class<*>) { startActivity(Intent(this, activityName)) }

    override fun finishAffinityActivity() = finishAffinity()
}
