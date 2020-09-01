@file:Suppress("DEPRECATION")

package com.gsm.bee_assistant_android.ui.calendar

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import com.gsm.bee_assistant_android.BR
import com.gsm.bee_assistant_android.R
import com.gsm.bee_assistant_android.base.BaseFragment
import com.gsm.bee_assistant_android.databinding.FragmentCalendarBinding
import kotlinx.android.synthetic.main.fragment_calendar.*
import kotlinx.android.synthetic.main.fragment_calendar.loading_progress
import javax.inject.Inject

class CalendarFragment : BaseFragment<FragmentCalendarBinding>(
    R.layout.fragment_calendar,
    BR.calendar
), CalendarContract.View {

    @Inject
    override lateinit var presenter: CalendarContract.Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.disposeDisposable()
    }

    override fun onResume() {
        super.onResume()

        setUI()
    }

    override fun onPause() {
        super.onPause()
        calendar_basic_textView.text = "날짜를 선택해 학사일정을 확인하세요."
        calendar_basic_textView.visibility = View.VISIBLE
        calendar_listView.visibility = View.INVISIBLE
        calendar_basic_textView.setTextColor(resources.getColor(R.color.darkGrayColor))
        presenter.compositeDisposable.clear()
    }

    private fun setUI() {

        if (!presenter.checkUserSchoolInfo()) {
            calendar_notification_textView.visibility = View.VISIBLE
            calendar_view.visibility = View.INVISIBLE
        } else {
            calendar_notification_textView.visibility = View.INVISIBLE
            calendar_view.visibility = View.VISIBLE
        }
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

            val adapter = ArrayAdapter(this.requireContext(), R.layout.list_view_item, scheduleList)

            calendar_listView.adapter = adapter
        }
    }

    override fun init() {

        setUI()

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