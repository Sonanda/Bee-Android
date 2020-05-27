package com.gsm.bee_assistant_android.ui.presenter

import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.gsm.bee_assistant_android.di.app.MyApplication
import com.gsm.bee_assistant_android.ui.contract.LoginContract
import com.gsm.bee_assistant_android.utils.PreferenceManager
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class LoginPresenter @Inject constructor(override val view: LoginContract.View) : LoginContract.Presenter {

    @Inject
    lateinit var pref : PreferenceManager

    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override val context: Context = view.getContext()

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
                    )
                        .subscribe { getUserInfo() }
                )

            }
        }
    }

    override fun getUserInfo() {

        val user = FirebaseAuth.getInstance().currentUser

        user.let {

            val name = it?.displayName
            val email = it?.email
            val photoUrl = it?.photoUrl

            pref.setData(MyApplication.Key.EMAIL.toString(), email.toString())

            Log.d("test", email.toString())
        }
    }

    override fun googleSignIn(signInIntent: Intent) = view.showLogin(signInIntent)

    override fun addDisposable(disposable: Disposable) { compositeDisposable.add(disposable) }

    override fun disposeDisposable() = compositeDisposable.dispose()
}