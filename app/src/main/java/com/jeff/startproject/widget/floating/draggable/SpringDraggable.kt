package com.jeff.startproject.widget.floating.draggable

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import com.jeff.startproject.MyApplication

class SpringDraggable : BaseDraggable() {
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
                val rawMoveX = event.rawX.toInt()
                val rawMoveY = (event.rawY - MyApplication.getStatusBarHeight()).toInt()
                // 更新移動的位置
                updateLocation(rawMoveX - viewDownX, rawMoveY - viewDownY)
            }
            MotionEvent.ACTION_UP -> {
                // 記錄移動的位置（相對屏幕的坐標）
                val rawMoveX = event.rawX.toInt()
                val rawMoveY = (event.rawY - MyApplication.getStatusBarHeight()).toInt()

                // 從移動的點回彈到邊界上
                val rangeXOfMove = screenWidth - (windowParams!!.width)
                val rawFinalX =
                    if (windowParams!!.x < rangeXOfMove / 2) {
                        // 回彈到屏幕左邊
                        0f
                    } else {
                        // 回彈到屏幕右邊
                        screenWidth.toFloat()
                    }

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
        get() {
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