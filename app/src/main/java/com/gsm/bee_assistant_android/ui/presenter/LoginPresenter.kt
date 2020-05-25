package com.gsm.bee_assistant_android.ui.presenter

import com.gsm.bee_assistant_android.ui.contract.LoginContract
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class LoginPresenter @Inject constructor(override val view: LoginContract.View) : LoginContract.Presenter {

    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun addDisposable(disposable: Disposable) { compositeDisposable.add(disposable) }

    override fun clearDisposable() = compositeDisposable.clear()
}