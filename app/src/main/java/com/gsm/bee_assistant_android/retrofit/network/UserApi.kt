package com.gsm.bee_assistant_android.retrofit.network

import com.gsm.bee_assistant_android.retrofit.domain.classroom.ClassroomTokenUpdate
import com.gsm.bee_assistant_android.retrofit.domain.user.UserToken
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*

interface UserApi {

    @FormUrlEncoded
    @POST("auth")
    fun getUserToken(@Field("email") email: String): Observable<UserToken>

    @FormUrlEncoded
    @POST("auth")
    fun getUserTokenTest(@Field("email") email: String): Call<UserToken>

    @PUT("auth")
    fun updateClassroomToken(@Body classroomTokenUpdate: ClassroomTokenUpdate): Observable<UserToken>

    @PUT("auth")
    fun updateClassroomTokenTest(@Body classroomTokenUpdate: ClassroomTokenUpdate): Call<UserToken>
}