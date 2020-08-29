package com.gsm.bee_assistant_android.ui.setschool

import android.content.Context
import android.util.Log
import com.gsm.bee_assistant_android.R
import com.gsm.bee_assistant_android.di.app.MyApplication
import com.gsm.bee_assistant_android.retrofit.domain.user.SchoolInfoUpdate
import com.gsm.bee_assistant_android.retrofit.repository.SchoolRepository
import com.gsm.bee_assistant_android.retrofit.repository.UserRepository
import com.gsm.bee_assistant_android.ui.main.MainActivity
import com.gsm.bee_assistant_android.utils.DataSingleton
import com.gsm.bee_assistant_android.utils.PreferenceManager
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class SetSchoolPresenter @Inject constructor(
    override val view: SetSchoolContract.View, context: Context,
    private val schoolApi: SchoolRepository,
    private val userApi: UserRepository
) : SetSchoolContract.Presenter {

    @Inject
    lateinit var pref : PreferenceManager

    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override val regionList: Array<String> = context.resources.getStringArray(R.array.region)
    override val schoolKindList: Array<String> = context.resources.getStringArray(R.array.school_kind)

    private val schoolKindIdList: Array<String> = context.resources.getStringArray(R.array.school_kind_id)
    private val regionIdList: Array<String> = context.resources.getStringArray(R.array.region_id)
    private val schoolTypeIdList: Array<String> = context.resources.getStringArray(R.array.school_type_id)

    override var schoolNameList: MutableList<String> = mutableListOf("학교 이름")

    override fun getSchoolInfo(schoolKind: String, region: String, schoolType: String) {

        view.setProgressStatus(true)

        addDisposable(
            schoolApi.getSchoolInfo(schoolKind, region, schoolType)
                .subscribe(
                    {
                        //Log.d("schoolNameTest", it.toString())

                        schoolNameList.clear()
                        schoolNameList.add("학교 이름")

                        val content = it.dataSearch!!.content!!

                        for(index in 0 until content.size) {
                            schoolNameList.add(content[index].schoolName!!)
                        }

                        view.setProgressStatus(false)
                    },
                    { Log.d("error", it.message.toString()) }
                )
        )
    }

    override fun getIdValue(schoolKind: String, region: String) {

        val schoolKindId = schoolKindIdList[schoolKindList.indexOf(schoolKind)]
        val regionId = regionIdList[regionList.indexOf(region)]
        val schoolTypeId = schoolTypeIdList[schoolKindList.indexOf(schoolKind)]

        getSchoolInfo(schoolKindId, regionId, schoolTypeId)
    }

    override fun checkSpinnerIndex(region: String, schoolType: String, schoolName: String): Boolean {

        if(regionList.indexOf(region) != 0 && schoolKindList.indexOf(schoolType) != 0 && schoolNameList.indexOf(schoolName) != 0) return true

        return false
    }

    override fun setSchoolName(schoolName: String) {
        DataSingleton.getInstance()?._userInfo?.name = schoolName
    }

    override fun setSchoolInfo(region: String, schoolType: String, schoolName: String) {

        view.showProgress()

        val email = DataSingleton.getInstance()?._userInfo?.email!!

        val schoolInfoUpdate = SchoolInfoUpdate(email, schoolType, region, schoolName)

        DataSingleton.getInstance()?._userInfo.let {
            it?.email = email
            it?.type = schoolType
            it?.region = region
        }

        addDisposable(
            userApi.updateSchoolInfo(schoolInfoUpdate)
                .subscribe(
                    {
                        pref.setData(MyApplication.Key.USER_TOKEN.toString(), it.token)

                        setSchoolName(schoolName).apply {
                            view.hideProgress()
                            view.startActivity(MainActivity::class.java)
                            view.finishActivity()
                        }
                    }, {}
                )
        )
    }

    override fun addDisposable(disposable: Disposable) { compositeDisposable.add(disposable) }

    override fun disposeDisposable() = compositeDisposable.dispose()
}