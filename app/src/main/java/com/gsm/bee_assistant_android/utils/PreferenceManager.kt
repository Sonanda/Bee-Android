@file:Suppress("DEPRECATION")

package com.gsm.bee_assistant_android.utils

import android.content.Context
import android.preference.PreferenceManager
import javax.inject.Inject

class PreferenceManager @Inject constructor(context: Context) {

    private val pref = PreferenceManager.getDefaultSharedPreferences(context)

    fun getData(key: String): String? = pref.getString(key, null)

    fun setData(key : String, value : String) = pref.edit().putString(key, value).commit()

}