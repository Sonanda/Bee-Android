package com.gsm.bee_assistant_android.ui.login.classroom

import android.util.Log
import com.gsm.bee_assistant_android.di.app.MyApplication
import com.gsm.bee_assistant_android.retrofit.domain.ClassroomToken
import com.gsm.bee_assistant_android.retrofit.network.ClassroomApi
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
    lateinit var pref: PreferenceManager

    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun getClassroomUrl() {

        val req = Request.Builder()
            .url(MyApplication.Base_Url + "classroom/getlink")
            .build()

            OkHttpClient().newCall(req).enqueue(object : Callback{

            override fun onResponse(call: Call, response: Response) {
                view.showClassroomWebView(response.body!!.string())
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

                        pref.setClassroomToken(MyApplication.Key.CLASSROOM_TOKEN.toString(), classroomToken)

                        Log.d("classroomToken", classroomToken.access_token)
                    }

                    override fun onComplete() {
                        view.hideProgress().apply {
                            if (pref.getData(MyApplication.Key.EMAIL.toString()) != "")
                                view.startActivity(SetSchoolActivity::class.java)
                            else
                                view.finishActivity()
                        }
                    }

                    override fun onError(e: Throwable) {}
                })
        )
    }

    override fun addDisposable(disposable: Disposable) { compositeDisposable.add(disposable) }

    override fun disposeDisposable() = compositeDisposable.dispose()
}