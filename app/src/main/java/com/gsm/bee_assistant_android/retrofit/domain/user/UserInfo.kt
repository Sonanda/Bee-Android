package com.gsm.bee_assistant_android.retrofit.domain.user

data class UserInfo(
    var email: String,
    var type: String? = null,
    var region: String? = null,
    var name: String? = null,
    var access_token: String? = null,
    var refresh_token: String? = null,
    var iat: Int? = null
)