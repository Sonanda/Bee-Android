package com.gsm.bee_assistant_android.ui.cafeteria

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.gsm.bee_assistant_android.R
import com.gsm.bee_assistant_android.base.BaseFragment
import com.gsm.bee_assistant_android.databinding.FragmentCafeteriaBinding
import com.gsm.bee_assistant_android.utils.ProgressUtil
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_cafeteria.*
import kotlinx.android.synthetic.main.meal_table.*
import javax.inject.Inject

class CafeteriaFragment : BaseFragment(), CafeteriaContract.View {

    @Inject
    override lateinit var presenter: CafeteriaContract.Presenter

    override lateinit var binding: FragmentCafeteriaBinding

    private lateinit var progress: ProgressUtil

    override fun onAttach(context: Context) {

        AndroidSupportInjection.inject(this)
        progress = ProgressUtil(this.context!!)

        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cafeteria, container, false)
        binding.cafeteria = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.disposeDisposable()
    }

    override fun init() {

        Log.i("data", cafeteria__calendarView.date.toString())

        cafeteria__calendarView.setOnDateChangeListener { _, year, month, dayOfMonth -> presenter.getMeal(year, month + 1, dayOfMonth) }
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

    override fun showProgress() = progress.show()

    override fun hideProgress() = progress.hide()

    override fun showToast(message: String) {}

    override fun startActivity(activityName: Class<*>) {}
}