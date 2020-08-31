package com.gsm.bee_assistant_android.ui.classroom

import androidx.lifecycle.MutableLiveData
import com.gsm.bee_assistant_android.retrofit.domain.classroom.ResponseClassList
import com.gsm.bee_assistant_android.retrofit.repository.ClassroomRepository
import com.gsm.bee_assistant_android.utils.DataSingleton
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.math.BigInteger
import javax.inject.Inject

class ClassroomPresenter @Inject constructor(
    override val view: ClassroomContract.View,
    override val compositeDisposable: CompositeDisposable,
    private val classroomApi: ClassroomRepository
) : ClassroomContract.Presenter {

    override var classList = MutableLiveData<ResponseClassList>()

    override fun getClassList() {

        view.showProgress()

        addDisposable(
            classroomApi.getClassList()
                .subscribe(
                    {
                        classList.postValue(it)

                        view.hideProgress()
                    }, { it.printStackTrace() }
                )
        )
    }

    override fun checkUserClassroomTokenInfo(): Boolean {

        val userInfo = DataSingleton.getInstance()?._userInfo

        if (userInfo?.access_token != "")
            return true

        return false
    }

    override fun getClassWork(classId: String) {

        view.showProgress()

        compositeDisposable.clear()

        addDisposable(
            classroomApi.getClassWork(BigInteger(classId))
                .map {

                    val titleList = arrayListOf<String>()
                    val linkList = arrayListOf<String>()

                    val classList = arrayListOf<ArrayList<String>>()

                    if (it.message != null) return@map classList

                    for (i in it.courseWork.indices) {

                        titleList.add(it.courseWork[i].title)
                        linkList.add(it.courseWork[i].alternateLink)
                    }

                    classList.add(titleList)
                    classList.add(linkList)

                    return@map classList
                }
                .subscribe(
                    {

                        view.showClassWork(it)
                        view.hideProgress()

                    }, { it.printStackTrace() }
                )
        )
    }

    override fun addDisposable(disposable: Disposable) { compositeDisposable.add(disposable) }

    override fun disposeDisposable() = compositeDisposable.dispose()
}