package com.gsm.bee_assistant_android.ui.setschool_dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import com.gsm.bee_assistant_android.R
import com.gsm.bee_assistant_android.base.BaseDialogFragment
import io.reactivex.Observable
import kotlinx.android.synthetic.main.search_bar.*
import kotlinx.android.synthetic.main.set_school_dialog.*
import javax.inject.Inject

class SetSchoolDialogFragment : BaseDialogFragment<ViewDataBinding>(
    R.layout.set_school_dialog
), SetSchoolDialogContract.View {

    @Inject
    override lateinit var presenter: SetSchoolDialogContract.Presenter

    private lateinit var schoolNameList: MutableList<String>

    var listener: (String) -> Unit = { inputSchoolName ->  presenter.checkSchoolName(inputSchoolName) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.set_school_dialog, container, false)
    }

    override fun onStart() {
        super.onStart()

        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)

        init()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        confirmButton.setOnClickListener {

            val inputSchoolName = schoolSearchTextView.text.toString()

            listener.invoke(inputSchoolName)

            presenter.setSchoolInfo(inputSchoolName)
        }

        cancelButton.setOnClickListener { dismissDialog() }
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter.disposeDisposable()
    }

    override fun dismissDialog() = dismiss()

    override fun init() {

        presenter.addDisposable(
            Observable.just(presenter.getSchoolName())
                .subscribe {
                    schoolNameList = it
                    //hideProgress()
                }
        )

        val adapter = context?.let {
            ArrayAdapter (
                it,
                android.R.layout.simple_dropdown_item_1line,
                schoolNameList
            )
        }

        schoolSearchTextView.let {
            it.threshold = 1
            it.setAdapter(adapter)
        }
    }

    override fun showProgress() = progress.show()

    override fun hideProgress() = progress.hide()

    override fun showToast(message: String) = Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()

    override fun startActivity(activityName: Class<*>) {}
}