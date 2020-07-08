package com.gsm.bee_assistant_android.ui.login.classroom

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.gsm.bee_assistant_android.R
import com.gsm.bee_assistant_android.databinding.ActivityClassroomLoginBinding
import com.jakewharton.rxbinding4.view.clicks
import dagger.android.AndroidInjection
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_classroom_login.*
import javax.inject.Inject

class ClassroomLoginActivity : AppCompatActivity(), ClassroomLoginContract.View {

    @Inject
    override lateinit var presenter: ClassroomLoginContract.Presenter

    override lateinit var binding: ActivityClassroomLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_classroom_login)
        binding.classroomLogin = this

        AndroidInjection.inject(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.disposeDisposable()
    }

    override fun onPause() {
        super.onPause()

        classroom_connect_layout.visibility = View.GONE
        input_classroomToken_layout.visibility = View.VISIBLE
    }

    override fun showClassroomWebView(url: String) = startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))

    override fun finishActivity() {

    }

    override fun init() {

    }

    override fun showToast(message: String) {

    }

    override fun startActivity(activityName: Class<*>) {

    }

    override fun onClickClassroomButton() = presenter.getClassroomUrl()

    override fun onClickSkipButton() {
        
    }

    override fun onBackPressed() {

        classroom_connect_layout.visibility = View.VISIBLE
        input_classroomToken_layout.visibility = View.GONE

        // super.onBackPressed()
    }
}