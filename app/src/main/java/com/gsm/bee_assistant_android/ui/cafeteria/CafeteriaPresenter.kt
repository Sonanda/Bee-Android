package com.gsm.bee_assistant_android.ui.cafeteria

import com.gsm.bee_assistant_android.retrofit.domain.school.Meal
import com.gsm.bee_assistant_android.retrofit.repository.SchoolRepository
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

        addDisposable(
            schoolApi.getMeal(year, month, day)
                .map {
                    val content = arrayOf(it.breakfast, it.launch, it.dinner)
                    val mealList = ArrayList<String>()

                    for (element in content) {

                        val meal = ArrayList<String>()

                        for (i in element.indices) {

                            if (element[i].contains("&amp;"))
                                meal.add(element[i].replace("&amp;", " / "))
                            else
                                meal.add(element[i])
                        }

                        val mealInfo = meal.toString().replace(",","\n").replace("[", "").replace("]", "")

                        if (mealInfo != "")
                            mealList.add(mealInfo)
                        else
                            mealList.add("급식 정보가 없습니다.")
                    }

                    return@map mealList
                }
                .subscribe(
                    {
                        view.showMeal(it)

                        view.hideProgress()
                    }, {}
                )
        )
    }

    override fun addDisposable(disposable: Disposable) { compositeDisposable.add(disposable) }

    override fun disposeDisposable() = compositeDisposable.clear()
}