package com.gsm.bee_assistant_android.retrofit.repository

import com.gsm.bee_assistant_android.BuildConfig
import com.gsm.bee_assistant_android.retrofit.domain.school.Meal
import com.gsm.bee_assistant_android.retrofit.domain.school.SchoolInfo
import com.gsm.bee_assistant_android.retrofit.network.SchoolApi
import com.gsm.bee_assistant_android.retrofit.network.SchoolInfoApi
import com.gsm.bee_assistant_android.utils.NetworkUtil
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SchoolRepository @Inject constructor(
    private val schoolInfoApi: SchoolInfoApi,
    private val schoolApi: SchoolApi,
    private val networkStatus: NetworkUtil
) {

    fun getSchoolInfo(schoolKind: String, region: String, schoolType: String) : Single<SchoolInfo> =
        schoolInfoApi.getSchoolInfo(apiKey = BuildConfig.SCHOOL_API_KEY, schoolKind = schoolKind, region =  region, schoolType = schoolType)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .retryWhen {
                Flowable.interval(3, TimeUnit.SECONDS)
                    .retryUntil {
                        if(networkStatus.networkInfo())
                            return@retryUntil true

                        false
                    }
            }

    fun getAllSchoolInfo(schoolKindId: String): Single<SchoolInfo> =
        schoolInfoApi.getAllSchoolInfo(apiKey = BuildConfig.SCHOOL_API_KEY, schoolKind = schoolKindId, perPage = "10000")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .retryWhen {
                Flowable.interval(3, TimeUnit.SECONDS)
                    .retryUntil {
                        if(networkStatus.networkInfo())
                            return@retryUntil true

                        false
                    }
            }

    fun getMeal(type: String, region: String, schoolName: String, year: Int, month: Int, day: Int): Single<Meal> =
        schoolApi.getMeal(type, region, schoolName, year, month, day)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .retryWhen {
                Flowable.interval(3, TimeUnit.SECONDS)
                    .retryUntil {
                        if(networkStatus.networkInfo())
                            return@retryUntil true

                        false
                    }
            }
}