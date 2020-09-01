package com.gsm.bee_assistant_android.ui.login.google

import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.gsm.bee_assistant_android.BR
import com.gsm.bee_assistant_android.R
import com.gsm.bee_assistant_android.base.BaseActivity
import com.gsm.bee_assistant_android.databinding.ActivityGoogleLoginBinding
import javax.inject.Inject

class GoogleLoginActivity : BaseActivity<ActivityGoogleLoginBinding>(
    R.layout.activity_google_login,
    BR.googleLogin
), GoogleLoginContract.View {

    @Inject
    override lateinit var presenter : GoogleLoginContract.Presenter

    private lateinit var googleSignInClient : GoogleSignInClient

    override fun onDestroy() {
        super.onDestroy()

        presenter.disposeDisposable()
    }

    override fun showLogin(signInIntent: Intent) = startActivityForResult(signInIntent, 100)

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        presenter.googleLogin(requestCode, resultCode, data)
    }

    override fun init() {}

    override fun onClickGoogleLoginButton() {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        presenter.googleSignIn(googleSignInClient.signInIntent)
    }

    override fun showProgress() = progress.show()

    override fun hideProgress() = progress.hide()

    override fun showToast(message: String) {}

    override fun startActivity(activityName: Class<*>) { startActivity(Intent(this, activityName)) }

    override fun finishActivity() = finish()
}
