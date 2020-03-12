package com.jeff.startproject.view.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.kaopiz.kprogresshud.KProgressHUD

abstract class BaseActivity<out B : ViewBinding> : AppCompatActivity() {

    val binding: B by lazy {
        getViewBinding()
    }

    val progressHUD: KProgressHUD by lazy {
        KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
    }

    abstract fun getViewBinding(): B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
    }
}