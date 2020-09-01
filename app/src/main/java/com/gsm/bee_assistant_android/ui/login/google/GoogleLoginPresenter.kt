package com.gsm.bee_assistant_android.ui.login.google

import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.Auth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.gsm.bee_assistant_android.di.app.MyApplication
import com.gsm.bee_assistant_android.retrofit.repository.UserRepository
import com.gsm.bee_assistant_android.ui.login.classroom.ClassroomLoginActivity
import com.gsm.bee_assistant_android.ui.main.MainActivity
import com.gsm.bee_assistant_android.ui.setschool.SetSchoolActivity
import com.gsm.bee_assistant_android.utils.DataSingleton
import com.gsm.bee_assistant_android.utils.PreferenceManager
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class GoogleLoginPresenter @Inject constructor(
    override val view: GoogleLoginContract.View,
    override val compositeDisposable: CompositeDisposable,
    private val pref: PreferenceManager,
    private val userApi: UserRepository
) : GoogleLoginContract.Presenter {

    override fun googleLogin(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == 100) {

            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)

            if (result!!.isSuccess) {

                val account = result.signInAccount
                val credential = GoogleAuthProvider.getCredential(account?.idToken, null)

                addDisposable(
                    Observable.just(
                        FirebaseAuth
                            .getInstance()
                            .signInWithCredential(credential)
                    ).subscribe(
                        { getToken(account?.email.toString()) }, { it.printStackTrace() }
                    )
                )
            }
        }
    }

    private fun getToken(email: String) {

        view.showProgress()

        addDisposable(
            userApi.getUserToken(email)
                .subscribe(
                    {
                        Log.i("userToken", it.token)
                        pref.setData(MyApplication.Key.USER_TOKEN.toString(), it.token)

                        getUserInfo()
                    }, {}
                )
        )
    }

    override fun getUserInfo() {
        addDisposable(
            userApi.getUserInfo(pref.getData(MyApplication.Key.USER_TOKEN.toString())!!)
                .subscribe(
                    {
                        DataSingleton.getInstance()?._userInfo = it

                        view.hideProgress().apply { checkUserInfoToChangeActivity() }
                    }, {}
                )
        )
    }

    override fun checkUserInfoToChangeActivity() {

        val userInfo = DataSingleton.getInstance()?._userInfo!!

        when {
            userInfo.access_token == null -> {
                view.startActivity(ClassroomLoginActivity::class.java)
                view.finishActivity()
            }
            userInfo.name == null -> {
                view.startActivity(SetSchoolActivity::class.java)
                view.finishActivity()
            }
            else -> {
                view.startActivity(MainActivity::class.java)
                view.finishActivity()
            }
        }
    }

    override fun setSchoolInfo() {

    }

    override fun googleSignIn(signInIntent: Intent) = view.showLogin(signInIntent)

    override fun addDisposable(disposable: Disposable) { compositeDisposable.add(disposable) }

    override fun disposeDisposable() = compositeDisposable.dispose()
}