package com.gsm.bee_assistant_android.retrofit.network

import com.gsm.bee_assistant_android.retrofit.domain.classroom.ClassroomTokenUpdate
import com.gsm.bee_assistant_android.retrofit.domain.user.SchoolInfoUpdate
import com.gsm.bee_assistant_android.retrofit.domain.user.UserInfo
import com.gsm.bee_assistant_android.retrofit.domain.user.UserToken
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.*

interface UserApi {

    @FormUrlEncoded
    @POST("auth")
    fun getUserToken(@Field("email") email: String): Single<UserToken>

    @FormUrlEncoded
    @POST("auth")
    fun getUserTokenTest(@Field("email") email: String): Call<UserToken>

    @PUT("auth")
    fun updateClassroomToken(@Body classroomTokenUpdate: ClassroomTokenUpdate): Single<UserToken>

    @PUT("auth")
    fun updateClassroomTokenTest(@Body classroomTokenUpdate: ClassroomTokenUpdate): Call<UserToken>

    @GET("auth")
    fun getUserInfo(@Header("x-access-token") token: String): Single<UserInfo>

    @GET("auth")
    fun getUserInfoTest(@Header("x-access-token") token: String): Call<UserInfo>

    @PATCH("auth")
    fun updateSchoolInfo(@Body schoolInfoUpdate: SchoolInfoUpdate): Single<UserToken>
}