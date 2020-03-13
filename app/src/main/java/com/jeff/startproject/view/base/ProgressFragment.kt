package com.jeff.startproject.view.base

import androidx.viewbinding.ViewBinding
import com.kaopiz.kprogresshud.KProgressHUD
import com.view.base.BaseFragment

abstract class ProgressFragment<out B : ViewBinding> : BaseFragment<B>() {

    val progressHUD: KProgressHUD by lazy {
        KProgressHUD.create(activity).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setCancellable(false)
    }
}