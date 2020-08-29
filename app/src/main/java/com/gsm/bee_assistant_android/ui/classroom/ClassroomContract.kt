package com.gsm.bee_assistant_android.ui.classroom

import com.gsm.bee_assistant_android.base.BasePresenter
import com.gsm.bee_assistant_android.base.BaseView

class ClassroomContract {
    interface View: BaseView<Presenter> {

    }

    interface Presenter: BasePresenter<View> {

    }
}