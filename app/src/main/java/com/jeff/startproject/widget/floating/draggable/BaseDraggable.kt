package com.jeff.startproject.widget.floating.draggable

import android.view.Gravity
import android.view.View
import android.view.View.OnTouchListener
import android.view.WindowManager
import java.lang.ref.WeakReference

abstract class BaseDraggable : OnTouchListener {
    protected var targetView: WeakReference<View>? = null
        private set
    protected var windowManager: WindowManager? = null
        private set
    protected var windowParams: WindowManager.LayoutParams? = null
        private set

    fun start(windowManager: WindowManager, view: View, layoutParams: WindowManager.LayoutParams) {
        this.targetView = WeakReference(view)
        this.windowManager = windowManager
        this.windowParams = layoutParams

        targetView?.get()?.setOnTouchListener(this)
    }

    fun stop() {
        targetView = null
        windowManager = null
        windowParams = null
    }

    protected fun updateLocation(x: Float, y: Float) {
        updateLocation(x.toInt(), y.toInt())
    }

    protected fun updateLocation(x: Int, y: Int) {
        targetView?.get()?.also { view ->
            windowManager?.also { windowManager ->
                windowParams?.also { windowParams ->
                    if (windowParams.x != x || windowParams.y != y) {
                        windowParams.x = x
                        windowParams.y = y
                        // 一定要先設置重心位置為左上角
                        windowParams.gravity = Gravity.TOP or Gravity.START

                        windowManager.updateViewLayout(view, windowParams)
                    }
                }
            }
        }
    }

    protected fun isTouchMove(downX: Float, upX: Float, downY: Float, upY: Float): Boolean {
        return downX != upX || downY != upY
    }
}