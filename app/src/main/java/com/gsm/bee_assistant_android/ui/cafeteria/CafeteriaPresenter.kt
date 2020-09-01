package com.gsm.bee_assistant_android.ui.cafeteria

import androidx.lifecycle.MutableLiveData
import com.gsm.bee_assistant_android.retrofit.repository.SchoolRepository
import com.gsm.bee_assistant_android.utils.DataSingleton
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class CafeteriaPresenter @Inject constructor(
    override val view: CafeteriaContract.View,
    override val compositeDisposable: CompositeDisposable,
    private val schoolApi: SchoolRepository
): CafeteriaContract.Presenter {

    override val mealList = MutableLiveData<ArrayList<String>>()

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

                        mealList.add(mealInfo)
                    }

                    return@map mealList
                }
                .subscribe(
                    {
                        mealList.postValue(it)
                        view.hideProgress()
                    }, {}
                )
        )
    }

    override fun checkUserSchoolInfo(): Boolean {

        val userInfo = DataSingleton.getInstance()?._userInfo

        if (userInfo?.type != "")
            return true

        return false
    }

    override fun addDisposable(disposable: Disposable) { compositeDisposable.add(disposable) }

    override fun disposeDisposable() = compositeDisposable.dispose()
}