package com.gsm.bee_assistant_android.ui.presenter

import com.gsm.bee_assistant_android.di.app.MyApplication
import com.gsm.bee_assistant_android.ui.LoginActivity
import com.gsm.bee_assistant_android.ui.MainActivity
import com.gsm.bee_assistant_android.ui.contract.SplashContract
import com.gsm.bee_assistant_android.utils.PreferenceManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SplashPresenter @Inject constructor(override val view: SplashContract.View) : SplashContract.Presenter {

    @Inject
    lateinit var pref: PreferenceManager

    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun checkUserInfo() {

        when {
            pref.getData(MyApplication.Key.EMAIL.toString()) != null -> view.startActivity(MainActivity::class.java).apply { view.finishActivity() }

            else ->
                addDisposable(
                    Observable.interval(1000 * 3, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { view.startActivity(LoginActivity::class.java).apply { view.finishActivity() } }
                )
        }
    }

    override fun addDisposable(disposable: Disposable) { compositeDisposable.add(disposable) }

    override fun disposeDisposable() = compositeDisposable.dispose()
}