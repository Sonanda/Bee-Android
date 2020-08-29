package com.gsm.bee_assistant_android.ui.calendar

import com.gsm.bee_assistant_android.retrofit.domain.school.Schedule
import com.gsm.bee_assistant_android.retrofit.repository.SchoolRepository
import com.gsm.bee_assistant_android.utils.DataSingleton
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class CalendarPresenter @Inject constructor(
    override val view: CalendarContract.View,
    private val schoolApi: SchoolRepository
): CalendarContract.Presenter {

    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun getSchedule(year: Int, month: Int, day: Int) {

        view.showProgress()

        compositeDisposable.clear()

        addDisposable(
            schoolApi.getSchedule(year, month, day)
                .map {
                    if (it.today.contains(","))
                        it.today.split(",")
                    else
                        listOf(it.today)
                }
                .subscribe(
                    {
                        setSchedule(it).let { scheduleList ->
                            view.showSchedule(scheduleList)
                            view.hideProgress()
                        }
                    }, {}
                )
        )
    }


    override fun setSchedule(scheduleList: List<String>): ArrayList<String> {

        val scheduleArrayList = arrayListOf<String>()

        for (i in scheduleList.indices)
            scheduleArrayList.add(scheduleList[i])

        return scheduleArrayList
    }

    override fun addDisposable(disposable: Disposable) { compositeDisposable.add(disposable) }

    override fun disposeDisposable() = compositeDisposable.clear()
}