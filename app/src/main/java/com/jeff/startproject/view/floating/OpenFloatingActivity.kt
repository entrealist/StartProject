package com.jeff.startproject.view.floating

import android.content.Intent
import android.os.Bundle
import com.jeff.startproject.R
import com.jeff.startproject.databinding.ActivityFloatingBinding
import com.jeff.startproject.widget.floating.FloatingWindowService
import com.jeff.startproject.widget.floating.FloatingWindowUtil
import com.jeff.startproject.widget.floating.draggable.MovingDraggable
import com.jeff.startproject.widget.floating.draggable.SpringDraggable
import com.view.base.BaseActivity
import kotlinx.android.synthetic.main.view_floating.view.*

class OpenFloatingActivity : BaseActivity<ActivityFloatingBinding>() {

    override fun getViewBinding() = ActivityFloatingBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnOpen1.setOnClickListener {
            val intent = Intent(this, FloatingWindowService::class.java)
            startService(intent)
        }

        binding.btnClose1.setOnClickListener {
            val intent = Intent(this, FloatingWindowService::class.java)
            stopService(intent)
        }

        binding.btnOpen2.setOnClickListener {
            FloatingWindowUtil.createFloatingWindow(this, MovingDraggable())
        }

        binding.btnOpen3.setOnClickListener {
            FloatingWindowUtil.createFloatingWindow(this, SpringDraggable())
        }

        binding.btnOpen4.setOnClickListener {
            FloatingWindowUtil.createFloatingWindowWithHorizontalDraggable(this)
        }

        binding.btnOpen5.setOnClickListener {
            val floatingLayout = layoutInflater.inflate(R.layout.view_floating, null)
            floatingLayout.btn.setOnClickListener {
                windowManager.removeView(floatingLayout)
            }
            FloatingWindowUtil.createFloatingWindow(this, false, floatingLayout)
        }
    }
}