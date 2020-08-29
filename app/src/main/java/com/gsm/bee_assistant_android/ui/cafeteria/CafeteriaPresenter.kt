package com.gsm.bee_assistant_android.ui.cafeteria

import com.gsm.bee_assistant_android.retrofit.domain.school.Meal
import com.gsm.bee_assistant_android.retrofit.repository.SchoolRepository
import com.gsm.bee_assistant_android.utils.DataSingleton
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class CafeteriaPresenter @Inject constructor(
    override val view: CafeteriaContract.View,
    private val schoolApi: SchoolRepository
): CafeteriaContract.Presenter {

    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun getMeal(year: Int, month: Int, day: Int) {

        view.showProgress()

        val userInfo = DataSingleton.getInstance()?._userInfo

        addDisposable(
            schoolApi.getMeal(year, month, day)
                .subscribe(
                    {
                        setMeal(it)

                        view.hideProgress()
                    }, {}
                )
        )
    }

    override fun setMeal(meal: Meal) {

        val content = arrayOf(meal.breakfast, meal.launch, meal.dinner)
        val mealList = ArrayList<String>()

        for (element in content) {

            val mealArrayList = ArrayList<String>()

            for (i in element.indices) {

                if (element[i].contains("&amp;"))
                    mealArrayList.add(element[i].replace("&amp;", " / "))
                else
                    mealArrayList.add(element[i])
            }

            val mealInfo = mealArrayList.toString().replace(",","\n").replace("[", "").replace("]", "")

            if (mealInfo != "")
                mealArrayList.add(mealInfo)
            else
                mealArrayList.add("급식 정보가 없습니다.")
        }

        view.showMeal(mealList)
    }

    override fun addDisposable(disposable: Disposable) { compositeDisposable.add(disposable) }

    override fun disposeDisposable() = compositeDisposable.clear()
}