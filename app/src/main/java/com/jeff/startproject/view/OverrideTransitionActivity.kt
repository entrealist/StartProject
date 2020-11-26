package com.jeff.startproject.view

import android.content.Intent
import android.os.Bundle
import com.jeff.startproject.databinding.ActivityOverrideTransitionBinding
import com.view.base.BaseActivity

class OverrideTransitionActivity : BaseActivity<ActivityOverrideTransitionBinding>() {

    companion object {
        var pageCount = 0
    }

    private val currentPage = pageCount

    override fun getViewBinding() = ActivityOverrideTransitionBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.tv1.text = currentPage.toString()

        binding.btnNext.setOnClickListener {
            pageCount++

            startActivity(Intent(this, OverrideTransitionActivity::class.java))
            if (currentPage % 2 == 0) overridePendingTransition(0, 0)
        }
    }

    override fun onBackPressed() {
        finish()
        if (currentPage % 2 == 0) overridePendingTransition(0, 0)
    }
}