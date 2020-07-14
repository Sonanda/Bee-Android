package com.gsm.bee_assistant_android.ui.setschool_dialog

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

class SetSchoolDialogPresenter @Inject constructor(override val view: SetSchoolDialogContract.View, context: Context) : SetSchoolDialogContract.Presenter {

    @Inject
    lateinit var schoolNameRetrofit: SchoolInfoApi

    @Inject
    lateinit var userRetrofit: UserApi

    @Inject
    lateinit var networkStatus: NetworkUtil

    @Inject
    lateinit var pref : PreferenceManager

    private val schoolKindIdList: Array<String> = context.resources.getStringArray(R.array.school_kind_id)

    private val schoolNameList = mutableListOf<String>()

    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun getSchoolName(): MutableList<String> {

        schoolNameList.clear()

        view.showProgress()

        compositeDisposable.add(
            Observable.range(1, 3)
                .subscribe{
                    schoolNameRetrofit.getAllSchoolInfo(apiKey = BuildConfig.SCHOOL_API_KEY, schoolKind = schoolKindIdList[it], perPage = "10000")
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

                                for(index in 0 until schoolInfo.dataSearch!!.content!!.size) {
                                    schoolNameList.add(schoolInfo.dataSearch.content!![index].schoolName!!)
                                }
                            }

                            override fun onComplete() { if (it < 2) view.hideProgress() }

                            override fun onError(e: Throwable) { Log.d("error", e.message.toString()) }
                        })
                }
        )

        return schoolNameList
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

                    override fun onComplete() { view.hideProgress() }

                    override fun onError(e: Throwable) {}
                })
        )
    }

    override fun checkSchoolName(inputSchoolName: String): String {

        for (index in schoolNameList.indices) {

            if (schoolNameList[index].contains(inputSchoolName)) {
                // 여기에 학교 정보 저장 구현
                return schoolNameList[index]
            }
        }

        return ""
    }

    override fun addDisposable(disposable: Disposable) { compositeDisposable.add(disposable) }

    override fun disposeDisposable() = compositeDisposable.dispose()

}