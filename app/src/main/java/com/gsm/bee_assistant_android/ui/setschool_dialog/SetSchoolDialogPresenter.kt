package com.gsm.bee_assistant_android.ui.setschool_dialog

import android.content.Context
import android.util.Log
import com.gsm.bee_assistant_android.R
import com.gsm.bee_assistant_android.di.app.MyApplication
import com.gsm.bee_assistant_android.retrofit.domain.user.SchoolInfoUpdate
import com.gsm.bee_assistant_android.retrofit.network.SchoolInfoApi
import com.gsm.bee_assistant_android.retrofit.repository.SchoolRepository
import com.gsm.bee_assistant_android.retrofit.repository.UserRepository
import com.gsm.bee_assistant_android.utils.DataSingleton
import com.gsm.bee_assistant_android.utils.PreferenceManager
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class SetSchoolDialogPresenter @Inject constructor(
    override val view: SetSchoolDialogContract.View,
    context: Context,
    private val schoolApi: SchoolRepository,
    private val userApi: UserRepository
) : SetSchoolDialogContract.Presenter {

    @Inject
    lateinit var schoolNameRetrofit: SchoolInfoApi

    @Inject
    lateinit var pref : PreferenceManager

    private val schoolKindIdList: Array<String> = context.resources.getStringArray(R.array.school_kind_id)

    private val schoolNameList = mutableListOf<String>()

    private val schoolTypeList = mutableListOf<String>()

    private val regionList = mutableListOf<String>()

    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun getSchoolName(): MutableList<String> {

        schoolNameList.clear()

        view.showProgress()

        compositeDisposable.add(
            Observable.range(1, 3)
                .subscribe{
                    schoolApi.getAllSchoolInfo(schoolKindIdList[it])
                        .subscribe(
                            { schoolInfo ->
                                val size = schoolInfo.dataSearch!!.content!!.size

                                for(index in 0 until size) {

                                    val content = schoolInfo.dataSearch.content!![index]

                                    schoolNameList.add(content.schoolName!!)

                                    regionList.add(content.region!!)

                                    //Log.d("test", schoolNameList[index])

                                    if (content.schoolGubun == null)
                                        checkSchoolType(content.schoolName)
                                    else
                                        schoolTypeList.add(content.schoolGubun)
                                }

                                if (it < 2) view.hideProgress()
                            },
                            { error -> Log.d("error", error.message.toString()) }
                        )
                }
        )

        return schoolNameList
    }

    private fun checkSchoolType(schoolName: String) {
        when {
            schoolName.contains("중학교") -> schoolTypeList.add("중학교")
            schoolName.contains("초등학교") -> schoolTypeList.add("초등학교")
        }
    }

    override fun setSchoolInfo(schoolName: String) {

        view.showProgress()

        val email = DataSingleton.getInstance()?._userInfo?.email!!

        val getRegionAndSchoolType = getRegionAndSchoolType(schoolName)

        val schoolInfoUpdate = SchoolInfoUpdate(email, getRegionAndSchoolType.second, getRegionAndSchoolType.first, schoolName)

        addDisposable(
            userApi.updateSchoolInfo(schoolInfoUpdate)
                .subscribe(
                    {
                        pref.setData(MyApplication.Key.USER_TOKEN.toString(), it.token).apply {
                            view.hideProgress()
                            view.dismissDialog()
                        }
                    }, {}
                )
        )
    }

    private fun getRegionAndSchoolType(schoolName: String): Pair<String, String> {

        val region = regionList[schoolNameList.indexOf(schoolName)]
        val schoolType = schoolTypeList[schoolNameList.indexOf(schoolName)]

        return Pair(region, schoolType)
    }

    override fun checkSchoolName(inputSchoolName: String): String {

        for (index in schoolNameList.indices) {

            if (schoolNameList[index].contains(inputSchoolName)) {

                return schoolNameList[index]
            }
        }

        return ""
    }

    override fun addDisposable(disposable: Disposable) { compositeDisposable.add(disposable) }

    override fun disposeDisposable() = compositeDisposable.dispose()

}