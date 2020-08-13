package com.gsm.bee_assistant_android.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.gsm.bee_assistant_android.R
import com.gsm.bee_assistant_android.base.BaseFragment
import com.gsm.bee_assistant_android.databinding.FragmentCalendarBinding
import javax.inject.Inject

class CalendarFragment : BaseFragment(), CalendarContract.View {

    @Inject
    override lateinit var presenter: CalendarContract.Presenter

    override lateinit var binding: FragmentCalendarBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calendar, container, false)
        binding.calendar = this

        return binding.root
    }

    override fun init() {}

    override fun showProgress() {}

    override fun hideProgress() {}

    override fun showToast(message: String) {}

    override fun startActivity(activityName: Class<*>) {}
}