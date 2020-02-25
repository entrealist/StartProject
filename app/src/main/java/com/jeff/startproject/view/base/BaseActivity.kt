package com.jeff.startproject.view.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.kaopiz.kprogresshud.KProgressHUD

abstract class BaseActivity<B : ViewBinding> : AppCompatActivity() {

    lateinit var binding: B

    val progressHUD: KProgressHUD by lazy {
        KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = getViewBinding()
        setContentView(binding.root)
    }

    abstract fun getViewBinding(): B
}