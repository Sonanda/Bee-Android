package com.gsm.bee_assistant_android.ui.login.classroom

import android.util.Log
import com.gsm.bee_assistant_android.BuildConfig
import com.gsm.bee_assistant_android.di.app.MyApplication
import com.gsm.bee_assistant_android.retrofit.domain.classroom.ClassroomToken
import com.gsm.bee_assistant_android.retrofit.domain.classroom.ClassroomTokenUpdate
import com.gsm.bee_assistant_android.retrofit.domain.user.UserToken
import com.gsm.bee_assistant_android.retrofit.network.ClassroomApi
import com.gsm.bee_assistant_android.retrofit.network.UserApi
import com.gsm.bee_assistant_android.ui.setschool.SetSchoolActivity
import com.gsm.bee_assistant_android.utils.PreferenceManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.*
import java.io.IOException
import javax.inject.Inject

class ClassroomLoginPresenter @Inject constructor(override val view: ClassroomLoginContract.View) : ClassroomLoginContract.Presenter {

    @Inject
    lateinit var classroomRetrofit: ClassroomApi

    @Inject
    lateinit var userRetrofit: UserApi

    @Inject
    lateinit var pref: PreferenceManager

    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun getClassroomUrl() {

        val req = Request.Builder()
            .url(BuildConfig.BASE_URL + "classroom/getlink")
            .build()

            OkHttpClient().newCall(req).enqueue(object : Callback{

            override fun onResponse(call: Call, response: Response) {
                view.showClassroomWebView(response.body!!.string()).apply { view.changeVisibility(true) }
            }

            override fun onFailure(call: Call, e: IOException) {}
        })
    }

    override fun getClassroomToken(token: String) {

        view.showProgress()

        addDisposable(
            classroomRetrofit.getClassroomToken(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableObserver<ClassroomToken>(){
                    override fun onNext(classroomToken: ClassroomToken) {

                        setClassroomToken(classroomToken)

                        Log.d("classroomToken", classroomToken.access_token.toString())
                    }

                    override fun onComplete() {}

                    override fun onError(e: Throwable) { Log.e("error", e.message.toString()) }
                })
        )
    }

    override fun setClassroomToken(classroomToken: ClassroomToken) {

        val email = pref.getData(MyApplication.Key.EMAIL.toString())!!
        val accessToken = classroomToken.access_token!!
        val refreshToken =  classroomToken.refresh_token!!

        Log.d("valueTest", "email : $email accessToken : $accessToken refreshToken : $refreshToken")

        val classroomTokenUpdate = ClassroomTokenUpdate(email = email, access_token = accessToken, refresh_token = refreshToken)

        addDisposable(
            userRetrofit.updateClassroomToken(classroomTokenUpdate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableObserver<UserToken>(){

                    override fun onNext(userToken: UserToken) { pref.setData(MyApplication.Key.USER_TOKEN.toString(), userToken.token) }

                    override fun onComplete() {
                        view.hideProgress().apply {
                            // 나중에 서버 DB에서 데이터 가져와서 데이터로 if문 작성해야함.
                            if (pref.getData(MyApplication.Key.EMAIL.toString()) != "") {
                                view.startActivity(SetSchoolActivity::class.java)
                                view.finishActivity()
                            }
                            else view.finishActivity()
                        }
                    }

                    override fun onError(e: Throwable) { Log.e("error", e.message.toString()) }
                })
        )

        pref.setClassroomToken(MyApplication.Key.CLASSROOM_TOKEN.toString(), classroomToken)
    }

    override fun addDisposable(disposable: Disposable) { compositeDisposable.add(disposable) }

    override fun disposeDisposable() = compositeDisposable.dispose()
}