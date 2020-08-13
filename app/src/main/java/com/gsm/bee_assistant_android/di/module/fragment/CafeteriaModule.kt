package com.gsm.bee_assistant_android.di.module.fragment

import com.gsm.bee_assistant_android.ui.cafeteria.CafeteriaContract
import com.gsm.bee_assistant_android.ui.cafeteria.CafeteriaFragment
import com.gsm.bee_assistant_android.ui.cafeteria.CafeteriaPresenter
import dagger.Binds
import dagger.Module

@Module
abstract class CafeteriaModule {

    @Binds
    abstract fun provideCafeteriaPresenter(presenter : CafeteriaPresenter): CafeteriaContract.Presenter

    @Binds
    abstract fun provideCafeteriaView(view : CafeteriaFragment): CafeteriaContract.View
}