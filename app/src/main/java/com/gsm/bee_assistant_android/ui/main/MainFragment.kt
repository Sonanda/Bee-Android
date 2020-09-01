package com.gsm.bee_assistant_android.ui.main

import com.gsm.bee_assistant_android.BR
import com.gsm.bee_assistant_android.R
import com.gsm.bee_assistant_android.base.BaseFragment
import com.gsm.bee_assistant_android.databinding.FragmentMainBinding
import javax.inject.Inject

class MainFragment : BaseFragment<FragmentMainBinding>(
    R.layout.fragment_main,
    BR.main
), MainContract.View {

    @Inject
    override lateinit var presenter: MainContract.Presenter

    override fun init() {}

    override fun showProgress() {}

    override fun hideProgress() {}

    override fun finishActivity() {}

    override fun finishAffinityActivity() {}

    override fun showToast(message: String) {}

    override fun startActivity(activityName: Class<*>) {}
}