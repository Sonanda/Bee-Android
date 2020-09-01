package com.gsm.bee_assistant_android.base

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.gsm.bee_assistant_android.utils.ProgressUtil
import dagger.android.AndroidInjection
import javax.inject.Inject

abstract class BaseActivity<B: ViewDataBinding>(
    @LayoutRes private val layoutResId: Int,
    private val BR: Int
) : AppCompatActivity() {

    @Inject
    lateinit var progress: ProgressUtil

    lateinit var binding: B

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        binding = DataBindingUtil.setContentView(this, layoutResId)
        binding.setVariable(BR, this)
        binding.lifecycleOwner = this

        AndroidInjection.inject(this)

        progress = ProgressUtil(this)
    }
}