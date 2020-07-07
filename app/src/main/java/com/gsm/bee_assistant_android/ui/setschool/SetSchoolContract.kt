package com.gsm.bee_assistant_android.ui.setschool

import com.gsm.bee_assistant_android.base.BasePresenter
import com.gsm.bee_assistant_android.base.BaseView

class SetSchoolContract {

    interface View : BaseView<Presenter> {

        fun setProgressStatus(bool: Boolean)
    }

    interface Presenter : BasePresenter<View> {

        val regionList: Array<String>
        val schoolKindList: Array<String>
        val schoolNameList: MutableList<String>

        fun getSchoolInfo(schoolKind: String, region: String, schoolType: String)

        fun getIdValue(schoolKind: String, region: String)

        fun setSchoolName(schoolName: String)
    }
}