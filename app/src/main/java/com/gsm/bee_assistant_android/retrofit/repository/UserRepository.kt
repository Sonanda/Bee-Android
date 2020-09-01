package com.gsm.bee_assistant_android.retrofit.repository

import com.gsm.bee_assistant_android.retrofit.domain.classroom.ClassroomTokenUpdate
import com.gsm.bee_assistant_android.retrofit.domain.user.SchoolInfoUpdate
import com.gsm.bee_assistant_android.retrofit.domain.user.UserInfo
import com.gsm.bee_assistant_android.retrofit.domain.user.UserToken
import com.gsm.bee_assistant_android.retrofit.network.UserApi
import com.gsm.bee_assistant_android.utils.NetworkUtil
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userApi: UserApi,
    private val networkStatus: NetworkUtil
){

    fun updateClassroomToken(classroomTokenUpdate: ClassroomTokenUpdate): Single<UserToken> =
        userApi.updateClassroomToken(classroomTokenUpdate)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .retryWhen {
                Flowable.interval(3, TimeUnit.SECONDS)
                    .onBackpressureBuffer()
                    .retryUntil {
                        if (networkStatus.networkInfo())
                            return@retryUntil true

                        false
                    }
            }

    fun getUserToken(email: String): Single<UserToken> =
        userApi.getUserToken(email)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .retryWhen {
                Flowable.interval(3, TimeUnit.SECONDS)
                    .onBackpressureBuffer()
                    .retryUntil {
                        if(networkStatus.networkInfo())
                            return@retryUntil true

                        false
                    }
            }

    fun getUserInfo(token: String): Single<UserInfo> =
        userApi.getUserInfo(token)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .retryWhen {
                Flowable.interval(3, TimeUnit.SECONDS)
                    .onBackpressureBuffer()
                    .retryUntil {
                        if(networkStatus.networkInfo())
                            return@retryUntil true

                        false
                    }
            }

    fun updateSchoolInfo(schoolInfoUpdate: SchoolInfoUpdate): Call<UserToken> =
        userApi.updateSchoolInfo(schoolInfoUpdate)

}