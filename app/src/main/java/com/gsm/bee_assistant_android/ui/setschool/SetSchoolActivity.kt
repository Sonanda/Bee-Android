package com.gsm.bee_assistant_android.ui.setschool

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.gsm.bee_assistant_android.R
import com.gsm.bee_assistant_android.base.BaseActivity
import com.gsm.bee_assistant_android.databinding.ActivitySetSchoolBinding
import com.gsm.bee_assistant_android.ui.main.MainActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_set_school.*
import javax.inject.Inject

class SetSchoolActivity : BaseActivity(), SetSchoolContract.View, AdapterView.OnItemSelectedListener, View.OnClickListener {

    @Inject
    override lateinit var presenter : SetSchoolContract.Presenter

    override lateinit var binding: ActivitySetSchoolBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_school)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_set_school)
        binding.setschool = this

        AndroidInjection.inject(this)

        init()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.disposeDisposable()
    }

    override fun onBackPressed() {}

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Log.d("spinnerTest", school_Kind_spinner.selectedItem.toString())

        val schoolKind = school_Kind_spinner.selectedItem.toString()
        val region = region_spinner.selectedItem.toString()

        presenter.getIdValue(schoolKind, region)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) { }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.setSchool_button -> {
                presenter.setSchoolName(school_Name_spinner.selectedItem.toString())
                startActivity(MainActivity::class.java)
                finish()
            }
            R.id.skip_button -> {
                startActivity(MainActivity::class.java)
                finish()
            }
        }
    }

    override fun init() {

        school_Kind_spinner.onItemSelectedListener = this
        region_spinner.onItemSelectedListener = this
        setSchool_button.setOnClickListener(this)
        skip_button.setOnClickListener(this)
    }

    override fun showToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    override fun startActivity(activityName: Class<*>) { startActivity(Intent(this, activityName)) }

    override fun setProgressStatus(bool: Boolean) {

        when(bool) {
            true -> loading_progress.visibility = View.VISIBLE
            else -> loading_progress.visibility = View.INVISIBLE
        }

        school_Name_spinner.isEnabled = !bool
    }

}
