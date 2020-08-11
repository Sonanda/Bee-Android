package com.gsm.bee_assistant_android.ui.splash

import android.util.Log
import com.gsm.bee_assistant_android.di.app.MyApplication
import com.gsm.bee_assistant_android.retrofit.network.UserApi
import com.gsm.bee_assistant_android.ui.login.classroom.ClassroomLoginActivity
import com.gsm.bee_assistant_android.ui.login.google.GoogleLoginActivity
import com.gsm.bee_assistant_android.ui.main.MainActivity
import com.gsm.bee_assistant_android.ui.setschool.SetSchoolActivity
import com.gsm.bee_assistant_android.utils.DataSingleton
import com.gsm.bee_assistant_android.utils.NetworkUtil
import com.gsm.bee_assistant_android.utils.PreferenceManager
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SplashPresenter @Inject constructor(override val view: SplashContract.View) : SplashContract.Presenter {

    @Inject
    lateinit var pref: PreferenceManager

    @Inject
    lateinit var userRetrofit: UserApi

    @Inject
    override lateinit var networkStatus: NetworkUtil

    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun checkUserInfo() {

        when {
            pref.getData(MyApplication.Key.USER_TOKEN.toString()) == "" ->
                addDisposable(
                    Observable.interval(1000 * 3, TimeUnit.MILLISECONDS)
                        .subscribe { view.startActivity(GoogleLoginActivity::class.java).apply { view.finishActivity() } }
                )

            else -> getUserInfo()
        }
    }

    override fun getUserInfo() {
        addDisposable(
            userRetrofit.getUserInfo(pref.getData(MyApplication.Key.USER_TOKEN.toString())!!)
                .subscribeOn(Schedulers.io())
                .retryWhen {
                    Flowable.interval(3, TimeUnit.SECONDS)
                        .retryUntil {
                            if(networkStatus.networkInfo())
                                return@retryUntil true

                            false
                        }
                }
                .subscribe(
                    {
                        Log.d("userInfoTest1", it.toString())

                        DataSingleton.getInstance()?._userInfo = it

                        checkUserInfoToChangeActivity()
                    }, {}
                )
        )
    }

    override fun checkUserInfoToChangeActivity() {

        val userInfo = DataSingleton.getInstance()?._userInfo!!

        Log.d("userInfoTest2", userInfo.toString())

        when {
            userInfo.access_token == null -> {
                view.startActivity(ClassroomLoginActivity::class.java)
                view.finishActivity()
            }
            userInfo.name  == null -> {
                view.startActivity(SetSchoolActivity::class.java)
                view.finishActivity()
            }
            else -> {
                view.startActivity(MainActivity::class.java)
                view.finishActivity()
            }
        }
    }

    override fun addDisposable(disposable: Disposable) { compositeDisposable.add(disposable) }

    override fun disposeDisposable() = compositeDisposable.dispose()
}