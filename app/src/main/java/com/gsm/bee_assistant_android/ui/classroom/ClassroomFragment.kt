package com.gsm.bee_assistant_android.ui.classroom

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.gsm.bee_assistant_android.BR
import com.gsm.bee_assistant_android.R
import com.gsm.bee_assistant_android.base.BaseFragment
import com.gsm.bee_assistant_android.databinding.FragmentClassroomBinding
import com.gsm.bee_assistant_android.ui.classroom.adapter.ClassroomAdapter
import com.gsm.bee_assistant_android.ui.login.classroom.ClassroomLoginActivity
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.class_work_list_view.*
import kotlinx.android.synthetic.main.fragment_classroom.*
import kotlinx.android.synthetic.main.fragment_classroom.loading_progress
import kotlinx.android.synthetic.main.list_view_item.*
import javax.inject.Inject

class ClassroomFragment : BaseFragment(), ClassroomContract.View {

    @Inject
    override lateinit var presenter: ClassroomContract.Presenter

    override lateinit var binding: FragmentClassroomBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)

        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_classroom, container, false)

        binding.setVariable(BR.classroom, this)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.disposeDisposable()
    }

    override fun onResume() {
        super.onResume()

        setUI()
    }

    override fun onPause() {
        super.onPause()
        recyclerView.visibility = View.INVISIBLE
        presenter.compositeDisposable.clear()
    }

    override fun init() {

        setUI()

        presenter.classList.observe(viewLifecycleOwner, Observer {

            if (!it.classList.isNullOrEmpty()) {
                recyclerView.visibility = View.VISIBLE
                classroom_notification_textView.visibility = View.INVISIBLE

                recyclerView.adapter?.notifyDataSetChanged()
                recyclerView.adapter = ClassroomAdapter(this, it)
            } else {
                classroom_notification_textView.text = "클래스룸 정보가 없습니다."
                classroom_notification_textView.visibility = View.VISIBLE
            }
        })
    }

    override fun showClassWork(classList: ArrayList<ArrayList<String>>) {

        BottomSheetDialog(this.requireContext()).let {
            it.setContentView(layoutInflater.inflate(R.layout.class_work_list_view, null))
            it.classWork_listView.adapter = ArrayAdapter(this.requireContext(), R.layout.list_view_item, classList[0])

            it.classWork_listView.setOnItemClickListener { _, _, position, _ ->
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(classList[1][position])))
                it.dismiss()
            }

            it.show()
        }
    }

    private fun setUI() {

        if (!presenter.checkUserClassroomTokenInfo()) {
            classroom_notification_textView.text = "클래스룸 로그인을 통해 과제를 확인하세요."
            classroom_notification_textView.visibility = View.VISIBLE
            connect_classroom_button.visibility = View.VISIBLE
            recyclerView.visibility = View.INVISIBLE
        } else {
            classroom_notification_textView.visibility = View.INVISIBLE
            connect_classroom_button.visibility = View.INVISIBLE
            recyclerView.visibility = View.INVISIBLE

            presenter.getClassList()
        }
    }

    override fun showProgress() { loading_progress.visibility = View.VISIBLE }

    fun onClickConnectClassroomButton() = startActivity(ClassroomLoginActivity::class.java)

    override fun hideProgress() { loading_progress.visibility = View.INVISIBLE }

    override fun showToast(message: String) {}

    override fun startActivity(activityName: Class<*>) { startActivity(Intent(this.requireContext(), activityName)) }
}