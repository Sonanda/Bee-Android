package com.gsm.bee_assistant_android.ui.presenter

import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.gsm.bee_assistant_android.R
import com.gsm.bee_assistant_android.di.app.MyApplication
import com.gsm.bee_assistant_android.retrofit.domain.SchoolInfo
import com.gsm.bee_assistant_android.retrofit.network.SchoolInfoApi
import com.gsm.bee_assistant_android.ui.contract.SetSchoolContract
import com.gsm.bee_assistant_android.utils.PreferenceManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SetSchoolPresenter @Inject constructor(override val view: SetSchoolContract.View, context: Context) : SetSchoolContract.Presenter {

    @Inject
    lateinit var pref : PreferenceManager

    @Inject
    lateinit var schoolNameRetrofit: SchoolInfoApi

    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override val regionList: Array<String> = context.resources.getStringArray(R.array.region)
    override val schoolKindList: Array<String> = context.resources.getStringArray(R.array.school_kind)

    private val schoolKindIdList: Array<String> = context.resources.getStringArray(R.array.school_kind_id)
    private val regionIdList: Array<String> = context.resources.getStringArray(R.array.region_id)
    private val schoolTypeIdList: Array<String> = context.resources.getStringArray(R.array.school_type_id)

    override var schoolNameList: MutableList<String> = mutableListOf("")

    override fun getUserInfo() {

        val user = FirebaseAuth.getInstance().currentUser

        user.let {

            //val name = it?.displayName
            val email = it?.email
            //val photoUrl = it?.photoUrl

            pref.setData(MyApplication.Key.EMAIL.toString(), email.toString())

            Log.d("test", email.toString())
        }
    }

    override fun getSchoolInfo(schoolKind: String, region: String, schoolType: String) {

        view.setProgressStatus(true)

        addDisposable(
            schoolNameRetrofit.getSchoolInfo(apiKey = MyApplication.Api_Key, schoolKind = schoolKind, region =  region, schoolType = schoolType)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(object : DisposableObserver<SchoolInfo>() {
                    override fun onNext(schoolInfo: SchoolInfo) {

                        //Log.d("schoolNameTest", schoolInfo.toString())

                        schoolNameList.clear()

                        for(index in 0 until schoolInfo.dataSearch!!.content!!.size) {
                            schoolNameList.add(schoolInfo.dataSearch.content!![index].schoolName!!)
                        }

                        pref.setData("getSchoolInfoTest", schoolInfo.dataSearch.content!![0].schoolName!!)
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

    override fun addDisposable(disposable: Disposable) { compositeDisposable.add(disposable) }

    override fun disposeDisposable() = compositeDisposable.dispose()
}