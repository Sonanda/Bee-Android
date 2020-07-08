package com.gsm.bee_assistant_android.retrofit.network

import com.gsm.bee_assistant_android.retrofit.domain.ClassroomToken
import io.reactivex.Observable
import retrofit2.http.*

interface ClassroomApi {

    @FormUrlEncoded
    @POST("classroom/getToken")
    fun getClassroomToken(@Field("code") code: String): Observable<ClassroomToken>
}