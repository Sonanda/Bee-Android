package com.gsm.bee_assistant_android.ui.cafeteria

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.gsm.bee_assistant_android.R
import com.gsm.bee_assistant_android.base.BaseFragment
import com.gsm.bee_assistant_android.databinding.FragmentCafeteriaBinding
import javax.inject.Inject

class CafeteriaFragment : BaseFragment(), CafeteriaContract.View {

    @Inject
    override lateinit var presenter: CafeteriaContract.Presenter

    override lateinit var binding: FragmentCafeteriaBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cafeteria, container, false)
        binding.cafeteria = this

        return binding.root
    }

    override fun init() {

    }

    override fun showProgress() {

    }

    override fun hideProgress() {

    }

    override fun showToast(message: String) {}

    override fun startActivity(activityName: Class<*>) {}
}