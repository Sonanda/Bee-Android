package com.gsm.bee_assistant_android.ui.classroom

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class ClassroomPresenter @Inject constructor(override val view: ClassroomContract.View) : ClassroomContract.Presenter {

    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun addDisposable(disposable: Disposable) { compositeDisposable.add(disposable) }

    override fun disposeDisposable() = compositeDisposable.clear()
}