package com.jeff.startproject.view.blur

import android.os.Bundle
import com.jeff.startproject.databinding.ActivityBlurBinding
import com.utils.extension.repeatAnimation
import com.view.base.BaseActivity

class BlurActivity : BaseActivity<ActivityBlurBinding>() {
    override fun getViewBinding(): ActivityBlurBinding {
        return ActivityBlurBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.ivCar.repeatAnimation()
    }
}