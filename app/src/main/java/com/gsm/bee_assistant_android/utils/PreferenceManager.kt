@file:Suppress("DEPRECATION")

package com.gsm.bee_assistant_android.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.gsm.bee_assistant_android.retrofit.domain.classroom.ClassroomToken
import javax.inject.Inject

class PreferenceManager @Inject constructor(context: Context) {

    private val pref: SharedPreferences by lazy {
        val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
        val masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)

        EncryptedSharedPreferences.create(
            "Ai-Bee",
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun getData(key: String): String? = pref.getString(key, "")

    fun setData(key : String, value : String) = pref.edit().putString(key, value).commit()

    fun getClassroomToken(key: String) : ClassroomToken = GsonBuilder().serializeNulls().create().fromJson(pref.getString(key, Gson().toJson(ClassroomToken())), ClassroomToken::class.java)

    fun setClassroomToken(key : String, classroomToken: ClassroomToken) = pref.edit().putString(key, GsonBuilder().create().toJson(classroomToken, ClassroomToken::class.java)).commit()
}