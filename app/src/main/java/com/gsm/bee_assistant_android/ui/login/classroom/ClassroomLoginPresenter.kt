package com.gsm.bee_assistant_android.ui.login.classroom

import android.util.Log
import com.gsm.bee_assistant_android.di.app.MyApplication
import com.gsm.bee_assistant_android.retrofit.domain.classroom.ClassroomLink
import com.gsm.bee_assistant_android.retrofit.domain.classroom.ClassroomToken
import com.gsm.bee_assistant_android.retrofit.domain.classroom.ClassroomTokenUpdate
import com.gsm.bee_assistant_android.retrofit.repository.ClassroomRepository
import com.gsm.bee_assistant_android.retrofit.repository.UserRepository
import com.gsm.bee_assistant_android.ui.main.MainActivity
import com.gsm.bee_assistant_android.ui.setschool.SetSchoolActivity
import com.gsm.bee_assistant_android.utils.DataSingleton
import com.gsm.bee_assistant_android.utils.PreferenceManager
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ClassroomLoginPresenter @Inject constructor(
    override val view: ClassroomLoginContract.View,
    private val pref: PreferenceManager,
    private val classroomApi: ClassroomRepository,
    private val userApi: UserRepository
) : ClassroomLoginContract.Presenter {

    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private val userInfo = DataSingleton.getInstance()?._userInfo

    override fun getClassroomUrl() {

        view.showProgress()

        classroomApi.getClassroomLink().enqueue(object : Callback<ClassroomLink> {

            override fun onResponse(call: Call<ClassroomLink>, response: Response<ClassroomLink>) {
                view.hideProgress()
                view.showClassroomWebView(response.body()!!.link).apply { view.changeVisibility(true) }
            }

            override fun onFailure(call: Call<ClassroomLink>, t: Throwable) { view.hideProgress() }
        })
    }

    override fun getClassroomToken(token: String) {

        view.showProgress()

        addDisposable(
            classroomApi.getClassroomToken(token)
                .subscribe(
                    {
                        setClassroomToken(it)

                        Log.d("classroomToken", it.access_token.toString())
                    },
                    {
                        Log.e("error", it.message.toString())
                        view.hideProgress()
                    }
                )
        )
    }

    override fun setClassroomToken(classroomToken: ClassroomToken) {

        val email = userInfo?.email!!
        val accessToken = classroomToken.access_token!!
        val refreshToken =  classroomToken.refresh_token!!

        //Log.d("valueTest", "email : $email accessToken : $accessToken refreshToken : $refreshToken")

        val classroomTokenUpdate = ClassroomTokenUpdate(email = email, access_token = accessToken, refresh_token = refreshToken)

        addDisposable(
            userApi.updateClassroomToken(classroomTokenUpdate)
                .subscribe(
                    {
                        Log.d("userToken", it.token)

                        pref.setData(MyApplication.Key.USER_TOKEN.toString(), it.token).apply {
                            userInfo.access_token = accessToken
                            userInfo.refresh_token = refreshToken
                            checkUserInfoToChangeActivity()
                        }
                    },
                    {
                        Log.e("error", it.message.toString())
                        view.hideProgress()
                    }
                )
        )
    }

    override fun checkUserInfoToChangeActivity() {

        view.hideProgress().apply {
            when {
                userInfo?.name == null -> {
                    view.startActivity(SetSchoolActivity::class.java)
                    view.finishActivity()
                }
                userInfo.access_token != "" -> {
                    view.finishActivity()
                }
                else -> {
                    view.startActivity(MainActivity::class.java)
                    view.finishActivity()
                }
            }
        }
    }

    override fun addDisposable(disposable: Disposable) { compositeDisposable.add(disposable) }

    override fun disposeDisposable() = compositeDisposable.dispose()
}