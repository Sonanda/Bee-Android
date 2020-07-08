package com.gsm.bee_assistant_android.ui.login.classroom

import com.gsm.bee_assistant_android.di.app.MyApplication
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import okhttp3.*
import java.io.IOException
import javax.inject.Inject

class ClassroomLoginPresenter @Inject constructor(override val view: ClassroomLoginContract.View) : ClassroomLoginContract.Presenter {

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

    override fun addDisposable(disposable: Disposable) { compositeDisposable.add(disposable) }

    override fun disposeDisposable() = compositeDisposable.dispose()
}