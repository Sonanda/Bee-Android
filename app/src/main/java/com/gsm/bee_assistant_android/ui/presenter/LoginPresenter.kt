package com.gsm.bee_assistant_android.ui.presenter

import android.content.Intent
import com.google.android.gms.auth.api.Auth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.gsm.bee_assistant_android.ui.SetSchoolActivity
import com.gsm.bee_assistant_android.ui.contract.LoginContract
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class LoginPresenter @Inject constructor(override val view: LoginContract.View) : LoginContract.Presenter {

    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

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
                    ).subscribe { view.startActivity(SetSchoolActivity::class.java).apply { view.finishActivity() } }
                )
            }
        }
    }

    override fun googleSignIn(signInIntent: Intent) = view.showLogin(signInIntent)

    override fun addDisposable(disposable: Disposable) { compositeDisposable.add(disposable) }

    override fun disposeDisposable() = compositeDisposable.dispose()
}