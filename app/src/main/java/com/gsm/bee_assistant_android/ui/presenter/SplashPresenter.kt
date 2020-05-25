package com.gsm.bee_assistant_android.ui.presenter

import com.gsm.bee_assistant_android.ui.contract.SplashContract
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class SplashPresenter @Inject constructor(override val view: SplashContract.View) : SplashContract.Presenter {

    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun addDisposable(disposable: Disposable) { compositeDisposable.add(disposable) }

    override fun clearDisposable() = compositeDisposable.clear()
}