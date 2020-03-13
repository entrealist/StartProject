package com.jeff.startproject.view.base

import androidx.viewbinding.ViewBinding
import com.kaopiz.kprogresshud.KProgressHUD
import com.view.base.BaseActivity

abstract class ProgressFragment<out B : ViewBinding> : BaseActivity<B>() {

    val progressHUD: KProgressHUD by lazy {
        KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
    }
}