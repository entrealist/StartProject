package com.jeff.startproject.widget.view.draggable

import android.view.View
import android.view.View.OnTouchListener
import java.lang.ref.WeakReference

abstract class BaseViewDraggable : OnTouchListener {
    protected var targetView: WeakReference<View>? = null
        private set

    fun setTargetView(view: View) {
        this.targetView = WeakReference(view)

        targetView?.get()?.setOnTouchListener(this)
    }

    fun removeTarget() {
        targetView = null
    }

    protected fun isTouchMove(downX: Float, upX: Float, downY: Float, upY: Float): Boolean {
        return downX != upX || downY != upY
    }
}