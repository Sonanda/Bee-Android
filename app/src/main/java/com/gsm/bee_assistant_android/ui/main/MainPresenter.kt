package com.gsm.bee_assistant_android.ui.main

import com.google.firebase.auth.FirebaseAuth
import com.gsm.bee_assistant_android.di.app.MyApplication
import com.gsm.bee_assistant_android.retrofit.domain.classroom.ClassroomToken
import com.gsm.bee_assistant_android.ui.login.google.GoogleLoginActivity
import com.gsm.bee_assistant_android.utils.PreferenceManager
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class MainPresenter @Inject constructor(override val view: MainContract.View) : MainContract.Presenter {

    @Inject
    lateinit var pref: PreferenceManager

    override val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var lastTimeBackPressed: Long = 0

    override fun backPressed() {
        if (System.currentTimeMillis() - lastTimeBackPressed < 2500)
            view.finishAffinityActivity()
        else {
            view.showToast("'뒤로' 버튼을 한번 더 누르면 종료합니다.")
            lastTimeBackPressed = System.currentTimeMillis()
        }
    }

    override fun logout() {

        pref.let {
            it.setData(MyApplication.Key.EMAIL.toString(), "")
            it.setData(MyApplication.Key.SCHOOL_NAME.toString(), "")
            it.setClassroomToken(MyApplication.Key.CLASSROOM_TOKEN.toString(),
                ClassroomToken(
                    access_token = null,
                    refresh_token = null
                )
            )
        }

        addDisposable(
            Observable.just(FirebaseAuth.getInstance().signOut())
                .subscribe { view.startActivity(GoogleLoginActivity::class.java).apply { view.finishActivity() } }
        )
    }

    override fun changeSchool(schoolName: String) { pref.setData(MyApplication.Key.SCHOOL_NAME.toString(), schoolName) }

    override fun getUserEmail(): String = pref.getData(MyApplication.Key.EMAIL.toString())!!

    override fun getSchoolName(): String = pref.getData(MyApplication.Key.SCHOOL_NAME.toString())!!

    override fun addDisposable(disposable: Disposable) { compositeDisposable.add(disposable) }

    override fun disposeDisposable() = compositeDisposable.dispose()
}