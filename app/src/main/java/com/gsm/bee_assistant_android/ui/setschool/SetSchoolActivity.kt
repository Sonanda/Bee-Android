package com.gsm.bee_assistant_android.ui.setschool

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.gsm.bee_assistant_android.BR
import com.gsm.bee_assistant_android.R
import com.gsm.bee_assistant_android.base.BaseActivity
import com.gsm.bee_assistant_android.databinding.ActivitySetSchoolBinding
import kotlinx.android.synthetic.main.activity_set_school.*
import javax.inject.Inject

class SetSchoolActivity : BaseActivity<ActivitySetSchoolBinding>(
    R.layout.activity_set_school,
    BR.setschool
), SetSchoolContract.View, AdapterView.OnItemSelectedListener, View.OnClickListener {

    @Inject
    override lateinit var presenter : SetSchoolContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_school)

        init()
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter.disposeDisposable()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        val schoolKind = school_Kind_spinner.selectedItem.toString()
        val region = region_spinner.selectedItem.toString()

        presenter.getIdValue(schoolKind, region)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) { }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.setSchool_button -> {

                val region = region_spinner.selectedItem.toString()
                val schoolType = school_Kind_spinner.selectedItem.toString()
                val schoolName = school_Name_spinner.selectedItem.toString()

                if(presenter.checkSpinnerIndex(region, schoolType, schoolName)) {
                    presenter.setSchoolInfo(region, schoolType, schoolName)
                }
            }
            R.id.skip_button -> presenter.setSchoolInfo("", "", "")
        }
    }

    override fun init() {

        school_Kind_spinner.onItemSelectedListener = this
        region_spinner.onItemSelectedListener = this
        setSchool_button.setOnClickListener(this)
        skip_button.setOnClickListener(this)
    }

    override fun showProgress() = progress.show()

    override fun hideProgress() = progress.hide()

    override fun finishActivity() = finish()

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
