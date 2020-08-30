package com.gsm.bee_assistant_android.ui.splash

import android.util.Log
import com.gsm.bee_assistant_android.di.app.MyApplication
import com.gsm.bee_assistant_android.retrofit.domain.user.UserInfo
import com.gsm.bee_assistant_android.retrofit.repository.UserRepository
import com.gsm.bee_assistant_android.ui.login.classroom.ClassroomLoginActivity
import com.gsm.bee_assistant_android.ui.login.google.GoogleLoginActivity
import com.gsm.bee_assistant_android.ui.main.MainActivity
import com.gsm.bee_assistant_android.ui.setschool.SetSchoolActivity
import com.gsm.bee_assistant_android.utils.DataSingleton
import com.gsm.bee_assistant_android.utils.PreferenceManager
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SplashPresenter @Inject constructor(
    override val view: SplashContract.View,
    private val pref: PreferenceManager,
    private val userApi: UserRepository
) : SplashContract.Presenter {

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
            userApi.getUserInfo(pref.getData(MyApplication.Key.USER_TOKEN.toString())!!)
                .subscribe(
                    {
                        Log.d("userInfoTest1", it.toString())

                        DataSingleton.getInstance()?._userInfo = it

                        checkUserInfoToChangeActivity(it)
                    }, {}
                )
        )
    }

    override fun checkUserInfoToChangeActivity(userInfo: UserInfo) {

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