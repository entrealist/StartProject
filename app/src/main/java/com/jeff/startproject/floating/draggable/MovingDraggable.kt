package com.jeff.startproject.floating.draggable

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View

class MovingDraggable : BaseDraggable() {
    private var viewDownX = 0f
    private var viewDownY = 0f

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                // 記錄按下的位置（相對 View 的坐標）
                viewDownX = event.x
                viewDownY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                // 記錄移動的位置（相對屏幕的坐標）
                val rawMoveX = event.rawX
                val rawMoveY = event.rawY - statusBarHeight
                // 更新移動的位置
                updateLocation(rawMoveX - viewDownX, rawMoveY - viewDownY)
            }
            MotionEvent.ACTION_UP ->
                // 如果用戶移動了手指，那麼就攔截本次觸摸事件，從而不讓點擊事件生效
                return isTouchMove(viewDownX, event.x, viewDownY, event.y)
            else -> {
            }
        }
        return false
    }
}