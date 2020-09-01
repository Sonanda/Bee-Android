package com.gsm.bee_assistant_android.ui.login.google

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.gsm.bee_assistant_android.R
import com.gsm.bee_assistant_android.base.BaseActivity
import com.gsm.bee_assistant_android.databinding.ActivityGoogleLoginBinding
import com.gsm.bee_assistant_android.utils.ProgressUtil
import dagger.android.AndroidInjection
import javax.inject.Inject

class GoogleLoginActivity : BaseActivity(), GoogleLoginContract.View {

    @Inject
    override lateinit var presenter : GoogleLoginContract.Presenter

    @Inject
    lateinit var progress: ProgressUtil

    override lateinit var binding: ActivityGoogleLoginBinding

    private lateinit var googleSignInClient : GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidInjection.inject(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_google_login)
        binding.googleLogin = this

        progress = ProgressUtil(this)
    }

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
