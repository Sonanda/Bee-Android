package com.gsm.bee_assistant_android.ui

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.gsm.bee_assistant_android.R
import com.gsm.bee_assistant_android.base.BaseActivity
import com.gsm.bee_assistant_android.ui.contract.SetSchoolContract
import javax.inject.Inject

class SetSchoolActivity : BaseActivity(), SetSchoolContract.View {

    @Inject
    override lateinit var presenter : SetSchoolContract.Presenter

    override lateinit var binding: ViewDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_school)
    }

    override fun init() {}

    override fun showKeyboard() {}

    override fun hideKeyboard() {}

    override fun showToast(message: String) {}

    override fun startActivity(activityName: Class<*>) {}
}
