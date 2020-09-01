package com.gsm.bee_assistant_android.ui.cafeteria

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.gsm.bee_assistant_android.BR
import com.gsm.bee_assistant_android.R
import com.gsm.bee_assistant_android.base.BaseFragment
import com.gsm.bee_assistant_android.databinding.FragmentCafeteriaBinding
import com.gsm.bee_assistant_android.utils.ProgressUtil
import kotlinx.android.synthetic.main.fragment_cafeteria.*
import kotlinx.android.synthetic.main.meal_table.*
import javax.inject.Inject

class CafeteriaFragment : BaseFragment<FragmentCafeteriaBinding>(
    R.layout.fragment_cafeteria,
    BR.cafeteria
), CafeteriaContract.View {

    @Inject
    override lateinit var presenter: CafeteriaContract.Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.disposeDisposable()
    }

    override fun onResume() {
        super.onResume()
        setUI()
    }

    override fun onPause() {
        super.onPause()
        presenter.compositeDisposable.clear()
    }

    override fun init() {

        setUI()

        cafeteria__calendarView.setOnDateChangeListener { _, year, month, dayOfMonth -> presenter.getMeal(year, month + 1, dayOfMonth) }
    }

    private fun setUI() {

        if (!presenter.checkUserSchoolInfo()) {
            cafeteria_notification_textView.visibility = View.VISIBLE
            cafeteria__calendarView.visibility = View.INVISIBLE
        } else {
            cafeteria_notification_textView.visibility = View.INVISIBLE
            cafeteria__calendarView.visibility = View.VISIBLE
        }
    }

    override fun showMeal(mealList: ArrayList<String>) {

        BottomSheetDialog(this.requireContext()).let {

            it.setContentView(layoutInflater.inflate(R.layout.meal_table, null))
            it.breakfast.text = mealList[0]
            it.launch.text = mealList[1]
            it.dinner.text = mealList[2]
            it.show()
        }
    }

    override fun showProgress() {

        progress = ProgressUtil(this.context!!)
        progress.show()
    }

    override fun hideProgress() = progress.hide()

    override fun showToast(message: String) {}

    override fun startActivity(activityName: Class<*>) {}
}