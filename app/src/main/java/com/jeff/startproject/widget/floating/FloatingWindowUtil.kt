package com.jeff.startproject.widget.floating

import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import com.jeff.startproject.MyApplication
import com.jeff.startproject.R
import com.jeff.startproject.widget.floating.draggable.BaseDraggable
import com.jeff.startproject.widget.view.draggable.TranslationXDraggable
import kotlinx.android.synthetic.main.view_floating.view.*

object FloatingWindowUtil {

    fun createFloatingWindow(context: Context, draggable: BaseDraggable) {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val layoutParams = WindowManager.LayoutParams().apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
            }
            flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            format = PixelFormat.TRANSLUCENT
            gravity = Gravity.TOP or Gravity.START
            width = (MyApplication.getScreenWidthPx() * .85f).toInt()
            height = WindowManager.LayoutParams.WRAP_CONTENT
            x = (MyApplication.getScreenWidthPx() * .075f).toInt()
            y = 0
        }

        val floatingLayout = inflater.inflate(R.layout.view_floating, null)
        windowManager.addView(floatingLayout, layoutParams)

        draggable.start(windowManager, floatingLayout, layoutParams)

        floatingLayout.btn.setOnClickListener {
            draggable.stop()
            windowManager.removeView(floatingLayout)
        }
    }

    fun createFloatingWindowWithHorizontalDraggable(context: Context) {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val layoutParams = WindowManager.LayoutParams().apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
            }
            flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            format = PixelFormat.TRANSLUCENT
            gravity = Gravity.TOP or Gravity.START
            width = WindowManager.LayoutParams.MATCH_PARENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
            x = 0
            y = (MyApplication.getScreenHeightPx() * .5f).toInt()
        }

        val floatingLayout = inflater.inflate(R.layout.view_floating, null)

        windowManager.addView(floatingLayout, layoutParams)

        val draggable = TranslationXDraggable()

        draggable.setTargetView(floatingLayout)

        floatingLayout.btn.setOnClickListener {
            draggable.removeTarget()
            windowManager.removeView(floatingLayout)
        }
    }


    fun createFloatingWindow(context: Context, isGlobal: Boolean = true, view: View) {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val layoutParams = WindowManager.LayoutParams().apply {
            if (isGlobal) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                } else {
                    type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
                }
            }
            flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            format = PixelFormat.TRANSLUCENT
            gravity = Gravity.TOP or Gravity.START
            width = (MyApplication.getScreenWidthPx() * .85f).toInt()
            height = WindowManager.LayoutParams.WRAP_CONTENT
            x = (MyApplication.getScreenWidthPx() * .075f).toInt()
            y = 0
        }

        windowManager.addView(view, layoutParams)
    }
}