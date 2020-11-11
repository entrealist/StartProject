package com.jeff.startproject.view.layout

import com.jeff.startproject.databinding.ActivityLayoutBinding
import com.view.base.BaseActivity

class LayoutActivity : BaseActivity<ActivityLayoutBinding>() {
    override fun getViewBinding(): ActivityLayoutBinding {
        return ActivityLayoutBinding.inflate(layoutInflater)
    }
}