package com.gsm.bee_assistant_android.ui.classroom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gsm.bee_assistant_android.R
import com.gsm.bee_assistant_android.base.BaseFragment
import com.gsm.bee_assistant_android.databinding.FragmentClassroomBinding
import javax.inject.Inject

class ClassroomFragment : BaseFragment(), ClassroomContract.View {

    @Inject
    override lateinit var presenter: ClassroomContract.Presenter

    override lateinit var binding: FragmentClassroomBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_classroom, container, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.disposeDisposable()
    }

    override fun init() {}

    override fun showProgress() {}

    override fun hideProgress() {}

    override fun showToast(message: String) {}

    override fun startActivity(activityName: Class<*>) {}
}