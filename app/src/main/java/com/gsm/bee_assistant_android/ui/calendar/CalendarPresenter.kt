package com.gsm.bee_assistant_android.ui.calendar

import com.gsm.bee_assistant_android.retrofit.repository.SchoolRepository
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
                .map {
                    val scheduleArrayList = arrayListOf<String>()

                    for (i in it.indices)
                        scheduleArrayList.add(it[i])

                    return@map scheduleArrayList
                }
                .subscribe(
                    {
                        view.showSchedule(it)
                        view.hideProgress()
                    }, {}
                )
        )
    }

    override fun addDisposable(disposable: Disposable) { compositeDisposable.add(disposable) }

    override fun disposeDisposable() = compositeDisposable.clear()
}