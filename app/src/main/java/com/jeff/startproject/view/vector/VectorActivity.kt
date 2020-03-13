package com.jeff.startproject.view.vector

import android.os.Bundle
import android.widget.Toast
import com.jeff.startproject.R
import com.jeff.startproject.databinding.ActivityVectorBinding
import com.utils.extension.repeatAnimation
import com.view.base.BaseActivity

/*
 * https://shapeshifter.design
 */
class VectorActivity : BaseActivity<ActivityVectorBinding>() {

    override fun getViewBinding(): ActivityVectorBinding {
        return ActivityVectorBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_share -> {
                    Toast.makeText(this, R.string.text_animation, Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

        binding.toolbar.navigationIcon?.repeatAnimation(binding.toolbar)

        binding.ivSmile.repeatAnimation()
        binding.ivCar.repeatAnimation()
    }
}