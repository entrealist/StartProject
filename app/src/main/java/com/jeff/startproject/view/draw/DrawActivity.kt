package com.jeff.startproject.view.draw

import com.jeff.startproject.databinding.ActivityDrawBinding
import com.view.base.BaseActivity

class DrawActivity : BaseActivity<ActivityDrawBinding>() {
    override fun getViewBinding(): ActivityDrawBinding = ActivityDrawBinding.inflate(layoutInflater)
}