package com.jeff.startproject.floating

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.media.projection.MediaProjectionManager
import android.os.Build
import android.os.IBinder
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import com.jeff.startproject.R
import com.log.JFLog
import kotlinx.android.synthetic.main.view_floating.view.*

class FloatingWindowService : Service() {

    companion object {
        var floatingWindowService: FloatingWindowService? = null
    }

    private lateinit var windowManager: WindowManager
    private lateinit var inflater: LayoutInflater
    private var layoutParams: WindowManager.LayoutParams? = null
    private var floatingLayout: View? = null

    private val display: DisplayMetrics
        get() = applicationContext.resources.displayMetrics

    private fun getScreenWidthPx() = display.widthPixels
    private fun getScreenHeightPx() = display.heightPixels

    private val mediaProjectionManager: MediaProjectionManager
        get() = getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager

    override fun onBind(intent: Intent?): IBinder? {
        JFLog.d("${this.hashCode()} onBind")
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        JFLog.d("W: ${getScreenWidthPx()}, H: ${getScreenHeightPx()}")
        JFLog.d("${this.hashCode()} onStartCommand")
        floatingWindowService = this
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCreate() {
        super.onCreate()
        JFLog.d("${this.hashCode()} onCreate")
        floatingWindowService = this
        windowManager = applicationContext.getSystemService(WINDOW_SERVICE) as WindowManager
        inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        initLayoutParams()
    }

    override fun onRebind(intent: Intent?) {
        JFLog.d("${this.hashCode()} onRebind")
        super.onRebind(intent)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        JFLog.d("${this.hashCode()} onUnbind")
        floatingWindowService = null
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        JFLog.d("${this.hashCode()} onDestroy")
        destroyFloatingWindow()
        floatingWindowService = null
        super.onDestroy()
    }

    private fun initLayoutParams() {
        if (layoutParams == null) {
            layoutParams = WindowManager.LayoutParams().apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                } else {
                    type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
                }
                flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                format = PixelFormat.TRANSLUCENT
                gravity = Gravity.TOP or Gravity.START
                width = (getScreenWidthPx() * .85f).toInt()
                height = WindowManager.LayoutParams.WRAP_CONTENT
                x = (getScreenWidthPx() * .075f).toInt()
                y = 0
            }
        }

        createFloatingWindow()
    }

    private fun createFloatingWindow() {
        if (floatingLayout == null) {
            floatingLayout = inflater.inflate(R.layout.view_floating, null)
            windowManager.addView(floatingLayout, layoutParams)

            //setFloatingNotTouchable()

            floatingLayout?.btn?.setOnClickListener {
                stopSelf()
            }

            JFLog.d("Width: ${floatingLayout!!.layoutParams.width}")
        }
    }

    private fun destroyFloatingWindow() {
        floatingLayout?.let {
            windowManager.removeView(it)
        }
    }

    // 需要註意的是設置了 FLAG_NOT_TOUCH_MODAL 必須要設置 FLAG_NOT_FOCUSABLE，否則就會導致用戶按返回鍵無效
    private fun setFloatingNotTouchable() {
        if (floatingLayout != null) {
            layoutParams?.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            windowManager.updateViewLayout(floatingLayout, layoutParams)
        }
    }

    private fun setFloatingTouchable() {
        if (floatingLayout != null) {
            layoutParams?.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            windowManager.updateViewLayout(floatingLayout, layoutParams)
        }
    }
}