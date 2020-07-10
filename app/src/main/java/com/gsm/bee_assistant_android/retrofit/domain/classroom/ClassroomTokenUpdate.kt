package com.gsm.bee_assistant_android.retrofit.domain.classroom

import com.google.gson.annotations.SerializedName

data class ClassroomTokenUpdate(
    @SerializedName("email")
    val email: String,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("region")
    val region: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("access_token")
    val access_token: String,
    @SerializedName("refresh_token")
    val refresh_token: String
)