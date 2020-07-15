package com.gsm.bee_assistant_android.base

import com.gsm.bee_assistant_android.utils.NetworkUtil
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

interface BasePresenter<T> {

    val view: T
    val compositeDisposable : CompositeDisposable
    val networkStatus: NetworkUtil

    fun addDisposable(disposable: Disposable)

    fun disposeDisposable()
}