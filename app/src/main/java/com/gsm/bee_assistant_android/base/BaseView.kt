package com.gsm.bee_assistant_android.base

interface BaseView<T> {

    val presenter : T

    fun init()

    fun showToast(message: String)

    fun startActivity(activityName : Class<*>)

    fun showProgress()

    fun hideProgress()
}