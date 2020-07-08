@file:Suppress("DEPRECATION")

package com.gsm.bee_assistant_android.utils

import android.content.Context
import android.preference.PreferenceManager
import com.google.gson.GsonBuilder
import com.gsm.bee_assistant_android.retrofit.domain.ClassroomToken
import javax.inject.Inject

class PreferenceManager @Inject constructor(context: Context) {

    private val pref = PreferenceManager.getDefaultSharedPreferences(context)

    fun getData(key: String): String? = pref.getString(key, "")

    fun setData(key : String, value : String) = pref.edit().putString(key, value).commit()

    fun getClassroomToken(key: String) : ClassroomToken = GsonBuilder().create().fromJson(pref.getString(key, null), ClassroomToken::class.java)

    fun setClassroomToken(key : String, classroomToken: ClassroomToken) = pref.edit().putString(key, GsonBuilder().create().toJson(classroomToken, ClassroomToken::class.java)).commit()
}