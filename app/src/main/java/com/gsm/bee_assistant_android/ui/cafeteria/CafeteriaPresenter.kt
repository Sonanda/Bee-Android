package com.gsm.bee_assistant_android.ui.cafeteria

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class CafeteriaPresenter @Inject constructor(override val view: CafeteriaContract.View): CafeteriaContract.Presenter {

    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun addDisposable(disposable: Disposable) { compositeDisposable.add(disposable) }

    override fun disposeDisposable() = compositeDisposable.dispose()
}