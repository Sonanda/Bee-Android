package com.gsm.bee_assistant_android.retrofit.network

import com.gsm.bee_assistant_android.retrofit.domain.school.SchoolInfo
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SchoolInfoApi {

    @GET("getOpenApi")
    fun getSchoolInfo(
        @Query("apiKey") apiKey: String,
        @Query("svcType") svcType: String = "api",
        @Query("svcCode") svcCode: String = "SCHOOL",
        @Query("contentType") contentType: String = "json",
        @Query("gubun") schoolKind: String,
        @Query("region") region: String,
        @Query("sch1") schoolType: String
    ) : Single<SchoolInfo>

    @GET("getOpenApi")
    fun getSchoolInfoTest(
        @Query("apiKey") apiKey: String,
        @Query("svcType") svcType: String = "api",
        @Query("svcCode") svcCode: String = "SCHOOL",
        @Query("contentType") contentType: String = "json",
        @Query("gubun") schoolKind: String,
        @Query("region") region: String,
        @Query("sch1") schoolType: String
    ) : Call<SchoolInfo>

    @GET("getOpenApi")
    fun getAllSchoolInfo(
        @Query("apiKey") apiKey: String,
        @Query("svcType") svcType: String = "api",
        @Query("svcCode") svcCode: String = "SCHOOL",
        @Query("contentType") contentType: String = "json",
        @Query("gubun") schoolKind: String,
        @Query("perPage") perPage: String = ""
    ): Single<SchoolInfo>
}