package com.jeff.startproject.view.vector

import android.os.Bundle
import android.widget.Toast
import com.jeff.startproject.R
import com.jeff.startproject.databinding.ActivityVectorBinding
import com.jeff.startproject.utils.repeatAnimation
import com.jeff.startproject.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_vector.*

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

        binding.toolbar.navigationIcon?.repeatAnimation(toolbar)

        iv_smile.repeatAnimation()
        iv_car.repeatAnimation()
    }
}