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
            schoolApi.getMeal(userInfo?.type.toString(), userInfo?.region.toString(), userInfo?.name.toString(), year, month, day)
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

            val meal = ArrayList<String>()

            for (i in element.indices) {

                if (element[i].contains("&amp;"))
                    meal.add(element[i].replace("&amp;", " / "))
                else
                    meal.add(element[i])
            }

            mealList.add(meal.toString().replace(",","\n").replace("[", "").replace("]", ""))
        }

        view.showMeal(mealList)
    }

    override fun addDisposable(disposable: Disposable) { compositeDisposable.add(disposable) }

    override fun disposeDisposable() = compositeDisposable.clear()
}