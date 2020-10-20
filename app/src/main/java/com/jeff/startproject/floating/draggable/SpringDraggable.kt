package com.jeff.startproject.floating.draggable

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View

class SpringDraggable : BaseDraggable() {
    private var viewDownX = 0f
    private var viewDownY = 0f

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        val rawMoveX: Int
        val rawMoveY: Int
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                // 記錄按下的位置（相對 View 的坐標）
                viewDownX = event.x
                viewDownY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                // 記錄移動的位置（相對屏幕的坐標）
                rawMoveX = event.rawX.toInt()
                rawMoveY = (event.rawY - statusBarHeight).toInt()
                // 更新移動的位置
                updateLocation(rawMoveX - viewDownX, rawMoveY - viewDownY)
            }
            MotionEvent.ACTION_UP -> {
                // 記錄移動的位置（相對屏幕的坐標）
                rawMoveX = event.rawX.toInt()
                rawMoveY = (event.rawY - statusBarHeight).toInt()
                // 獲取當前屏幕的寬度
                val screenWidth = screenWidth
                // 自動回彈吸附
                val rawFinalX: Float
                rawFinalX = if (rawMoveX < screenWidth / 2) {
                    // 回彈到屏幕左邊
                    0f
                } else {
                    // 回彈到屏幕右邊
                    screenWidth.toFloat()
                }
                // 從移動的點回彈到邊界上
                startAnimation(rawMoveX - viewDownX, rawFinalX - viewDownX, rawMoveY - viewDownY)
                // 如果用戶移動了手指，那麼就攔截本次觸摸事件，從而不讓點擊事件生效
                return isTouchMove(viewDownX, event.x, viewDownY, event.y)
            }
            else -> {
            }
        }
        return false
    }

    /**
     * 獲取屏幕的寬度
     */
    private val screenWidth: Int
        private get() {
            val outMetrics = DisplayMetrics()
            windowManager?.defaultDisplay?.getMetrics(outMetrics)
            return outMetrics.widthPixels
        }

    private fun startAnimation(startX: Float, endX: Float, y: Float) {
        val animator = ValueAnimator.ofFloat(startX, endX)
        animator.duration = 500
        animator.addUpdateListener { animation -> updateLocation(animation.animatedValue as Float, y) }
        animator.start()
    }
}