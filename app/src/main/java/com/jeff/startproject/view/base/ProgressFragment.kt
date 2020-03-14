package com.jeff.startproject.view.base

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.kaopiz.kprogresshud.KProgressHUD
import com.view.base.BaseFragment
import com.view.base.BaseViewModel

abstract class ProgressFragment<out B : ViewBinding, out VM : BaseViewModel> : BaseFragment<B, VM>() {

    private val progressHUD: KProgressHUD by lazy {
        KProgressHUD.create(activity).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setCancellable(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchViewModel()?.also { viewModel ->
            viewModel.processing.observe(viewLifecycleOwner, Observer {
                if (it) {
                    progressHUD.show()
                } else {
                    progressHUD.dismiss()
                }
            })
        }
    }
}