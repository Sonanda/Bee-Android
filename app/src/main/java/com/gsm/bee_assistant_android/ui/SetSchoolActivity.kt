package com.gsm.bee_assistant_android.ui

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.gsm.bee_assistant_android.R
import com.gsm.bee_assistant_android.base.BaseActivity
import com.gsm.bee_assistant_android.databinding.ActivitySetSchoolBinding
import com.gsm.bee_assistant_android.ui.contract.SetSchoolContract
import dagger.android.AndroidInjection
import javax.inject.Inject

class SetSchoolActivity : BaseActivity(), SetSchoolContract.View {

    @Inject
    override lateinit var presenter : SetSchoolContract.Presenter

    override lateinit var binding: ActivitySetSchoolBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_school)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_set_school)
        binding.setschool = this

        AndroidInjection.inject(this)

        init()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.disposeDisposable()
    }

    override fun init() {
        presenter.getUserInfo()
    }

    override fun showKeyboard() {}

    override fun hideKeyboard() {}

    override fun showToast(message: String) {}

    override fun startActivity(activityName: Class<*>) { startActivity(Intent(this, activityName)) }
}
