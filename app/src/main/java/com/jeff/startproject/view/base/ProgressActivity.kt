package com.jeff.startproject.view.base

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.kaopiz.kprogresshud.KProgressHUD
import com.view.base.BaseActivity
import com.view.base.BaseViewModel

abstract class ProgressActivity<out B : ViewBinding, out VM : BaseViewModel> : BaseActivity<B>() {

    abstract fun fetchViewModel(): VM?

    private val progressHUD: KProgressHUD by lazy {
        KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setCancellable(false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fetchViewModel()?.also { viewModel ->
            viewModel.processing.observe(this, Observer {
                if (it) {
                    progressHUD.show()
                } else {
                    progressHUD.dismiss()
                }
            })
        }
    }
}