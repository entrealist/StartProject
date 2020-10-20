package com.jeff.startproject.floating.draggable

import android.graphics.Rect
import android.view.Gravity
import android.view.View
import android.view.View.OnTouchListener
import android.view.WindowManager

abstract class BaseDraggable : OnTouchListener {
    protected var rootView: View? = null
        private set
    protected var windowManager: WindowManager? = null
        private set
    protected var windowParams: WindowManager.LayoutParams? = null
        private set

    fun start(windowManager: WindowManager, view: View, layoutParams: WindowManager.LayoutParams) {
        this.rootView = view
        this.windowManager = windowManager
        this.windowParams = layoutParams

        rootView?.setOnTouchListener(this)
    }

    fun stop() {
        rootView = null
        windowManager = null
        windowParams = null
    }

    /**
     * 獲取狀態欄的高度
     */
    val statusBarHeight: Int
        get() {
            val frame = Rect()
            rootView?.getWindowVisibleDisplayFrame(frame)
            return frame.top
        }

    protected fun updateLocation(x: Float, y: Float) {
        updateLocation(x.toInt(), y.toInt())
    }

    protected fun updateLocation(x: Int, y: Int) {
        windowManager?.also { windowManager ->
            if (windowParams?.x != x || windowParams?.y != y) {
                windowParams?.x = x
                windowParams?.y = y
                // 一定要先設置重心位置為左上角
                windowParams?.gravity = Gravity.TOP or Gravity.START

                windowManager.updateViewLayout(rootView, windowParams)
            }
        }
    }

    protected fun isTouchMove(downX: Float, upX: Float, downY: Float, upY: Float): Boolean {
        return downX != upX || downY != upY
    }
}