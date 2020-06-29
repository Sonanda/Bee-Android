package com.gsm.bee_assistant_android.ui.presenter

import android.content.Context
import android.util.Log
import com.gsm.bee_assistant_android.R
import com.gsm.bee_assistant_android.di.app.MyApplication
import com.gsm.bee_assistant_android.retrofit.domain.SchoolInfo
import com.gsm.bee_assistant_android.retrofit.network.SchoolInfoApi
import com.gsm.bee_assistant_android.ui.contract.SetSchoolDialogContract
import com.gsm.bee_assistant_android.utils.NetworkUtil
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
    lateinit var networkStatus: NetworkUtil

    private val schoolKindIdList: Array<String> = context.resources.getStringArray(R.array.school_kind_id)

    private val schoolNameList = mutableListOf<String>()

    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun getSchoolName(): MutableList<String> {

        schoolNameList.clear()

        view.showProgress()

        compositeDisposable.add(
            Observable.range(0, 3)
                .subscribe{
                    schoolNameRetrofit.getAllSchoolInfo(apiKey = MyApplication.Api_Key, schoolKind = schoolKindIdList[it], perPage = "10000")
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

                            override fun onComplete() { if (it < 1) view.hideProgress() }

                            override fun onError(e: Throwable) { Log.d("error", e.message.toString()) }
                        })
                }
        )



        return schoolNameList
    }

    override fun addDisposable(disposable: Disposable) { compositeDisposable.add(disposable) }

    override fun disposeDisposable() = compositeDisposable.dispose()

}