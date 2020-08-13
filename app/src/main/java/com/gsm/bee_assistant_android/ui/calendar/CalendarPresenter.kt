package com.gsm.bee_assistant_android.ui.calendar

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class CalendarPresenter @Inject constructor(override val view: CalendarContract.View): CalendarContract.Presenter {

    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun addDisposable(disposable: Disposable) { compositeDisposable.add(disposable) }

    override fun disposeDisposable() = compositeDisposable.dispose()
}