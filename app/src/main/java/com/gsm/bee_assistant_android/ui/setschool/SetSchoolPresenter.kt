package com.gsm.bee_assistant_android.ui.setschool

import android.content.Context
import android.util.Log
import com.gsm.bee_assistant_android.BuildConfig
import com.gsm.bee_assistant_android.R
import com.gsm.bee_assistant_android.di.app.MyApplication
import com.gsm.bee_assistant_android.retrofit.domain.school.SchoolInfo
import com.gsm.bee_assistant_android.retrofit.domain.user.SchoolInfoUpdate
import com.gsm.bee_assistant_android.retrofit.domain.user.UserToken
import com.gsm.bee_assistant_android.retrofit.network.SchoolInfoApi
import com.gsm.bee_assistant_android.retrofit.network.UserApi
import com.gsm.bee_assistant_android.ui.main.MainActivity
import com.gsm.bee_assistant_android.utils.DataSingleton
import com.gsm.bee_assistant_android.utils.NetworkUtil
import com.gsm.bee_assistant_android.utils.PreferenceManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SetSchoolPresenter @Inject constructor(override val view: SetSchoolContract.View, context: Context) : SetSchoolContract.Presenter {

    @Inject
    lateinit var pref : PreferenceManager

    @Inject
    lateinit var schoolNameRetrofit: SchoolInfoApi

    @Inject
    lateinit var userRetrofit: UserApi

    @Inject
    lateinit var networkStatus: NetworkUtil

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
            schoolNameRetrofit.getSchoolInfo(apiKey = BuildConfig.SCHOOL_API_KEY, schoolKind = schoolKind, region =  region, schoolType = schoolType)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .retryWhen {
                    Observable.interval(3, TimeUnit.SECONDS)
                        .retryUntil {
                            if(networkStatus.networkInfo())
                                return@retryUntil true

                            false
                        }
                }
                .subscribeWith(object : DisposableObserver<SchoolInfo>() {
                    override fun onNext(schoolInfo: SchoolInfo) {

                        //Log.d("schoolNameTest", schoolInfo.toString())

                        schoolNameList.clear()
                        schoolNameList.add("학교 이름")

                        for(index in 0 until schoolInfo.dataSearch!!.content!!.size) {
                            schoolNameList.add(schoolInfo.dataSearch.content!![index].schoolName!!)
                        }
                    }

                    override fun onComplete() { view.setProgressStatus(false) }

                    override fun onError(e: Throwable) { Log.d("error", e.message.toString()) }
                })
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

        addDisposable(
            userRetrofit.updateSchoolInfo(schoolInfoUpdate)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(object: DisposableObserver<UserToken>(){
                    override fun onNext(userToken: UserToken) {
                        pref.setData(MyApplication.Key.USER_TOKEN.toString(), userToken.token)
                    }

                    override fun onComplete() {
                        setSchoolName(schoolName).apply {
                            view.hideProgress()
                            view.startActivity(MainActivity::class.java)
                            view.finishActivity()
                        }
                    }

                    override fun onError(e: Throwable) {}
                })
        )
    }

    override fun addDisposable(disposable: Disposable) { compositeDisposable.add(disposable) }

    override fun disposeDisposable() = compositeDisposable.dispose()
}