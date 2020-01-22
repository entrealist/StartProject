package com.jeff.startproject.view.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kaopiz.kprogresshud.KProgressHUD

abstract class BaseActivity : AppCompatActivity() {

    val progressHUD: KProgressHUD by lazy {
        KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(getLayoutId())
    }

    abstract fun getLayoutId(): Int
}