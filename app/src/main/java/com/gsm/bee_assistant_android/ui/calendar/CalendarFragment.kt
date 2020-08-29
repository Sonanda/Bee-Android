package com.gsm.bee_assistant_android.ui.calendar

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.gsm.bee_assistant_android.R
import com.gsm.bee_assistant_android.base.BaseFragment
import com.gsm.bee_assistant_android.databinding.FragmentCalendarBinding
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_calendar.*
import kotlinx.android.synthetic.main.fragment_calendar.loading_progress
import javax.inject.Inject

class CalendarFragment : BaseFragment(), CalendarContract.View {

    @Inject
    override lateinit var presenter: CalendarContract.Presenter

    override lateinit var binding: FragmentCalendarBinding

    override fun onAttach(context: Context) {

        AndroidSupportInjection.inject(this)

        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calendar, container, false)
        binding.calendar = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.disposeDisposable()
    }

    override fun showSchedule(scheduleList: ArrayList<String>) {

        if (scheduleList[0] == "") {

            calendar_basic_textView.text = "학사일정이 없습니다."
            calendar_basic_textView.visibility = View.VISIBLE
            calendar_listView.visibility = View.INVISIBLE
            calendar_basic_textView.setTextColor(resources.getColor(R.color.black))

        } else {

            calendar_basic_textView.text = "날짜를 선택해 학사일정을 확인하세요."
            calendar_basic_textView.visibility = View.INVISIBLE
            calendar_listView.visibility = View.VISIBLE

            val adapter = ArrayAdapter(this.requireContext(), R.layout.calendar_list_view_item, scheduleList)

            calendar_listView.adapter = adapter
        }
    }

    override fun init() {

        calendar_calendarView.setOnDateChangeListener { _, year, month, dayOfMonth -> presenter.getSchedule(year, month + 1, dayOfMonth) }
    }

    override fun showProgress()  {
        loading_progress.visibility = View.VISIBLE
        calendar_basic_textView.visibility = View.INVISIBLE
        calendar_listView.visibility = View.INVISIBLE
    }

    override fun hideProgress() { loading_progress.visibility = View.INVISIBLE }

    override fun showToast(message: String) {}

    override fun startActivity(activityName: Class<*>) {}
}