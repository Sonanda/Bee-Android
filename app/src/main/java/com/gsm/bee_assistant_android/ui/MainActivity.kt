package com.gsm.bee_assistant_android.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import com.gsm.bee_assistant_android.R
import com.gsm.bee_assistant_android.base.BaseActivity
import com.gsm.bee_assistant_android.databinding.ActivityMainBinding
import com.gsm.bee_assistant_android.databinding.NavigationHeaderBinding
import com.gsm.bee_assistant_android.ui.contract.MainContract
import dagger.android.AndroidInjection
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.title_bar.*
import javax.inject.Inject

class MainActivity : BaseActivity(), MainContract.View {

    @Inject
    override lateinit var presenter : MainContract.Presenter

    override lateinit var binding: ActivityMainBinding
    private lateinit var bindingNavigationHeader: NavigationHeaderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.main = this

        bindingNavigationHeader = NavigationHeaderBinding.bind(navigation_view.getHeaderView(0))
        bindingNavigationHeader.headerNavigation = this

        AndroidInjection.inject(this)

        init()
    }

    override fun init() {
        show_navigation_bar_button.setOnClickListener(this)
        bindingNavigationHeader.userEmail.text = presenter.getUserEmail()
        bindingNavigationHeader.userSchoolName.text = presenter.getSchoolName()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.disposeDisposable()
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.show_navigation_bar_button ->
                presenter.addDisposable(
                    Observable.just(drawer_layout.openDrawer(GravityCompat.END))
                        .subscribe()
                )
        }
    }

    override fun onClickLogoutButton() = presenter.logout()

    override fun onClickChangeSchoolButton() {

        val dialog = SetSchoolDialogFragment()

        dialog.let {

            it.listener = { schoolName ->
                bindingNavigationHeader.userSchoolName.text = schoolName
                presenter.changeSchool(schoolName)
            }

            it.show(supportFragmentManager, "setSchoolDialog")
        }
    }

    override fun onBackPressed() {

        when {
            drawer_layout.isDrawerOpen(GravityCompat.END) -> drawer_layout.closeDrawers()
            else -> presenter.backPressed()
        }
    }

    override fun showToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    override fun startActivity(activityName: Class<*>) { startActivity(Intent(this, activityName)) }

    override fun finishAffinityActivity() = finishAffinity()

    override fun finishActivity() = finish()
}
