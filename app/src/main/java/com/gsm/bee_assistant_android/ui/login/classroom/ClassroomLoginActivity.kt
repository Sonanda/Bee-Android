package com.gsm.bee_assistant_android.ui.login.classroom

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.gsm.bee_assistant_android.R
import com.gsm.bee_assistant_android.databinding.ActivityClassroomLoginBinding
import com.gsm.bee_assistant_android.retrofit.domain.classroom.ClassroomToken
import com.gsm.bee_assistant_android.ui.setschool.SetSchoolActivity
import com.gsm.bee_assistant_android.utils.ProgressUtil
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.activity_classroom_login.*
import javax.inject.Inject

class ClassroomLoginActivity : AppCompatActivity(), ClassroomLoginContract.View {

    @Inject
    override lateinit var presenter: ClassroomLoginContract.Presenter

    @Inject
    lateinit var progress: ProgressUtil

    override lateinit var binding: ActivityClassroomLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidInjection.inject(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_classroom_login)
        binding.classroomLogin = this

        progress = ProgressUtil(this)
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter.disposeDisposable()

    }

    override fun showClassroomWebView(url: String) = startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))

    override fun finishActivity() = finish()

    override fun init() {}

    override fun showToast(message: String) {}

    override fun showProgress() = progress.show()

    override fun hideProgress() = progress.hide()

    override fun startActivity(activityName: Class<*>) { startActivity(Intent(this, activityName)) }

    override fun onClickClassroomButton() = presenter.getClassroomUrl()

    override fun onClickSkipButton() = startActivity(SetSchoolActivity::class.java).apply { presenter.setClassroomToken(ClassroomToken(access_token = "", refresh_token = "")); finish() }

    override fun onClickCheckButton() = presenter.getClassroomToken(token_editText.text.toString())

    override fun onBackPressed() {
        if (classroom_connect_layout.visibility == View.GONE)
            changeVisibility(false)
        else super.onBackPressed()
    }

    override fun changeVisibility(bool: Boolean) {

        runOnUiThread {
            when(bool) {
                true -> {
                    classroom_connect_layout.visibility = View.GONE
                    input_classroomToken_layout.visibility = View.VISIBLE
                }

                false-> {
                    classroom_connect_layout.visibility = View.VISIBLE
                    input_classroomToken_layout.visibility = View.GONE
                }
            }
        }
    }
}