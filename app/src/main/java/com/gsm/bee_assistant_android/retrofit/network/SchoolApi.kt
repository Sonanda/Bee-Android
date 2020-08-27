package com.gsm.bee_assistant_android.retrofit.network

import com.gsm.bee_assistant_android.retrofit.domain.school.Meal
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface SchoolApi {

    @GET("school/meal")
    fun getMeal(
        @Query("type") type: String,
        @Query("region") region: String,
        @Query("school") schoolName: String,
        @Query("year") year: Int,
        @Query("month") month: Int,
        @Query("day") day: Int
    ) : Single<Meal>
}