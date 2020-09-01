package com.gsm.bee_assistant_android.ui.cafeteria

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
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

        presenter.mealList.observe(this, Observer { mealList ->

            BottomSheetDialog(this.requireContext(), R.style.BottomSheetDialogTheme).let {

                it.setContentView(layoutInflater.inflate(R.layout.meal_table, null))

                when {
                    mealList[0] != "" && mealList[1] != "" && mealList[2] != "" -> {

                        setBottomSheetDialogUI(it, true)

                        it.breakfast.text = mealList[0]
                        it.launch.text = mealList[1]
                        it.dinner.text = mealList[2]
                    }
                    mealList[0] == "" && mealList[1] == "" && mealList[2] == "" -> {

                        setBottomSheetDialogUI(it, false)

                        it.meal_notification_textView.visibility = View.VISIBLE
                    }
                    else -> {

                        if (mealList[0] != "") {

                            setBottomSheetDialogUI(it, true)
                            it.breakfast.text = mealList[0]
                        } else {
                            it.title_breakfast.visibility = View.GONE
                            it.breakfast.visibility = View.GONE
                        }

                        if (mealList[1] != "") {

                            setBottomSheetDialogUI(it, true)
                            it.launch.text = mealList[1]
                        } else {
                            it.title_launch.visibility = View.GONE
                            it.launch.visibility = View.GONE
                        }

                        if (mealList[2] != "") {

                            setBottomSheetDialogUI(it, true)
                            it.dinner.text = mealList[2]
                        } else {
                            it.title_dinner.visibility = View.GONE
                            it.dinner.visibility = View.GONE
                        }
                    }
                }

                it.show()
            }
        })
    }

    private fun setBottomSheetDialogUI(it: BottomSheetDialog, state: Boolean) {

        if (state) {

            it.title_breakfast.visibility = View.VISIBLE
            it.title_launch.visibility = View.VISIBLE
            it.title_dinner.visibility = View.VISIBLE

            it.breakfast.visibility = View.VISIBLE
            it.launch.visibility = View.VISIBLE
            it.dinner.visibility = View.VISIBLE

            it.meal_notification_textView.visibility = View.GONE
        } else {

            it.title_breakfast.visibility = View.GONE
            it.title_launch.visibility = View.GONE
            it.title_dinner.visibility = View.GONE

            it.breakfast.visibility = View.GONE
            it.launch.visibility = View.GONE
            it.dinner.visibility = View.GONE
        }
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

    override fun showProgress() {

        progress = ProgressUtil(this.context!!)
        progress.show()
    }

    override fun hideProgress() = progress.hide()

    override fun showToast(message: String) {}

    override fun startActivity(activityName: Class<*>) {}
}