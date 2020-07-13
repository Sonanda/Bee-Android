package com.gsm.bee_assistant_android.ui.splash

import com.gsm.bee_assistant_android.di.app.MyApplication
import com.gsm.bee_assistant_android.ui.login.classroom.ClassroomLoginActivity
import com.gsm.bee_assistant_android.ui.login.google.GoogleLoginActivity
import com.gsm.bee_assistant_android.ui.main.MainActivity
import com.gsm.bee_assistant_android.utils.PreferenceManager
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SplashPresenter @Inject constructor(override val view: SplashContract.View) : SplashContract.Presenter {

    @Inject
    lateinit var pref: PreferenceManager

    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun checkUserInfo() {

        when {
            pref.getClassroomToken(MyApplication.Key.CLASSROOM_TOKEN.toString()).access_token == null
                    && pref.getData(MyApplication.Key.EMAIL.toString()) != "" -> view.startActivity(ClassroomLoginActivity::class.java).apply { view.finishActivity() }

            pref.getData(MyApplication.Key.EMAIL.toString()) != "" -> view.startActivity(MainActivity::class.java).apply { view.finishActivity() }

            else -> {
                addDisposable(
                    Observable.interval(1000 * 3, TimeUnit.MILLISECONDS)
                        .subscribe { view.startActivity(GoogleLoginActivity::class.java).apply { view.finishActivity() } }
                )
            }
        }
    }

    override fun addDisposable(disposable: Disposable) { compositeDisposable.add(disposable) }

    override fun disposeDisposable() = compositeDisposable.dispose()
}