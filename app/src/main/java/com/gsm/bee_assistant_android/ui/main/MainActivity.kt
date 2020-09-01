package com.gsm.bee_assistant_android.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.gsm.bee_assistant_android.R
import com.gsm.bee_assistant_android.base.BaseActivity
import com.gsm.bee_assistant_android.databinding.ActivityMainBinding
import com.gsm.bee_assistant_android.databinding.NavigationHeaderBinding
import com.gsm.bee_assistant_android.ui.cafeteria.CafeteriaFragment
import com.gsm.bee_assistant_android.ui.calendar.CalendarFragment
import com.gsm.bee_assistant_android.ui.classroom.ClassroomFragment
import com.gsm.bee_assistant_android.ui.setschool_dialog.SetSchoolDialogFragment
import dagger.android.AndroidInjection
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_cafeteria.*
import kotlinx.android.synthetic.main.fragment_calendar.*
import kotlinx.android.synthetic.main.title_bar.*
import javax.inject.Inject

class MainActivity : BaseActivity(), MainContract.View, BottomNavigationView.OnNavigationItemSelectedListener {

    @Inject
    override lateinit var presenter : MainContract.Presenter

    override lateinit var binding: ActivityMainBinding

    private lateinit var bindingNavigationHeader: NavigationHeaderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidInjection.inject(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.main = this

        bindingNavigationHeader = NavigationHeaderBinding.bind(navigation_view.getHeaderView(0))
        bindingNavigationHeader.headerNavigation = this

        init()
    }

    override fun init() {
        show_navigation_bar_button.setOnClickListener(this)
        bindingNavigationHeader.userEmail.text = presenter.getUserEmail()
        bindingNavigationHeader.userSchoolName.text = presenter.getSchoolName()

        viewPager.adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        viewPager.registerOnPageChangeCallback(ViewPagerPageChangeCallback())

        bottomNavigation.setOnNavigationItemSelectedListener(this)
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

                if(checkSchoolNameIsBlank(schoolName)) {
                    bindingNavigationHeader.userSchoolName.text = schoolName

                    when(viewPager.currentItem) {

                        1 -> {
                            cafeteria_notification_textView.visibility = View.INVISIBLE
                            cafeteria__calendarView.visibility = View.VISIBLE
                        }

                        2 -> {
                            calendar_notification_textView.visibility = View.INVISIBLE
                            calendar_view.visibility = View.VISIBLE
                        }
                    }
                }
            }

            it.show(supportFragmentManager, "setSchoolDialog")
        }
    }

    private fun checkSchoolNameIsBlank(schoolName: String): Boolean {

        if (schoolName != "")
            return true

        return false
    }

    override fun onBackPressed() {

        when {
            drawer_layout.isDrawerOpen(GravityCompat.END) -> drawer_layout.closeDrawers()
            else -> presenter.backPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.page_home -> {
                viewPager.currentItem = 0
                return true
            }

            R.id.page_cafeteria -> {
                viewPager.currentItem = 1
                return true
            }

            R.id.page_calendar -> {
                viewPager.currentItem = 2
                return true
            }

            R.id.page_classroom -> {
                viewPager.currentItem = 3
                return true
            }
        }

        return false
    }

    private inner class ViewPagerPageChangeCallback: ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            bottomNavigation.selectedItemId = when (position) {
                0 -> R.id.page_home
                1 -> R.id.page_cafeteria
                2 -> R.id.page_calendar
                3 -> R.id.page_classroom
                else -> error("error")
            }
        }
    }

    private inner class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle) {

        override fun getItemCount(): Int = 4

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> MainFragment()
                1 -> CafeteriaFragment()
                2 -> CalendarFragment()
                3 -> ClassroomFragment()
                else -> error("error")
            }
        }

    }

    override fun showProgress() {}

    override fun hideProgress() {}

    override fun showToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    override fun startActivity(activityName: Class<*>) { startActivity(Intent(this, activityName)) }

    override fun finishAffinityActivity() = finishAffinity()

    override fun finishActivity() = finish()
}
