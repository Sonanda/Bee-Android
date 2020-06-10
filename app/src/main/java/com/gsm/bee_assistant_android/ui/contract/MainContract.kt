package com.gsm.bee_assistant_android.ui.contract

import com.gsm.bee_assistant_android.base.BasePresenter
import com.gsm.bee_assistant_android.base.BaseView

class MainContract {

    interface View : BaseView<Presenter>, android.view.View.OnClickListener {

        fun finishAffinityActivity()
    }

    interface Presenter : BasePresenter<View> {

        fun backPressed()
    }
}