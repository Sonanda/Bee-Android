package com.gsm.bee_assistant_android.ui.cafeteria

import com.gsm.bee_assistant_android.base.BasePresenter
import com.gsm.bee_assistant_android.base.BaseView
import com.gsm.bee_assistant_android.retrofit.domain.school.Meal

class CafeteriaContract {
    interface View: BaseView<Presenter> {

        fun showMeal(mealList: ArrayList<String>)
    }

    interface Presenter: BasePresenter<View> {

        fun getMeal(year: Int, month: Int, day: Int)

        fun setMeal(meal: Meal)
    }
}