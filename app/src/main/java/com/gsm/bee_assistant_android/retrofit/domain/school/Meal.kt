package com.gsm.bee_assistant_android.retrofit.domain.school

import com.google.gson.annotations.SerializedName

data class Meal(
    @SerializedName("조식")
    val breakfast: List<String>,
    @SerializedName("중식")
    val launch: List<String>,
    @SerializedName("석식")
    val dinner: List<String>
)