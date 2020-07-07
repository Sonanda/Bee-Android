package com.gsm.bee_assistant_android.ui.setschool_dialog

import com.gsm.bee_assistant_android.base.BasePresenter
import com.gsm.bee_assistant_android.base.BaseView

class SetSchoolDialogContract {

    interface View : BaseView<Presenter> {

        fun showProgress()

        fun hideProgress()
    }

    interface Presenter : BasePresenter<View> {

        fun getSchoolName(): MutableList<String>
    }
}