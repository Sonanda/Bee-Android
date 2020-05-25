package com.gsm.bee_assistant_android.ui.presenter

import com.gsm.bee_assistant_android.ui.contract.SetSchoolContract
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class SetSchoolPresenter @Inject constructor(override val view: SetSchoolContract.View) : SetSchoolContract.Presenter {

    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun addDisposable(disposable: Disposable) { compositeDisposable.add(disposable) }

    override fun clearDisposable() = compositeDisposable.clear()
}