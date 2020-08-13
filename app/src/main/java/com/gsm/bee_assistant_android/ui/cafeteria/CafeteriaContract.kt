package com.gsm.bee_assistant_android.ui.cafeteria

import com.gsm.bee_assistant_android.base.BasePresenter
import com.gsm.bee_assistant_android.base.BaseView

class CafeteriaContract {
    interface View: BaseView<Presenter> {

    }

    interface Presenter: BasePresenter<View> {

    }
}