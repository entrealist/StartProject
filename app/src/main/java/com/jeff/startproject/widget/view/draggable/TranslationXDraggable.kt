package com.jeff.startproject.widget.view.draggable

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import com.jeff.startproject.MyApplication
import kotlin.math.absoluteValue

class TranslationXDraggable : BaseViewDraggable() {

    private var viewDownX = 0f

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        val rawMoveX: Int
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                // 記錄按下的位置（相對 View 的坐標）
                viewDownX = event.x
            }
            MotionEvent.ACTION_MOVE -> {
                // 記錄移動的位置（相對屏幕的坐標）
                rawMoveX = event.rawX.toInt()
                // 更新移動的位置
                updateLocation(rawMoveX - viewDownX)
            }
            MotionEvent.ACTION_UP -> {
                // 記錄移動的位置（相對屏幕的坐標）
                rawMoveX = event.rawX.toInt()

                // 移動超過設定距離, 移出View.
                if (rawMoveX - viewDownX > MyApplication.getScreenWidthPx() / 2) {
                    startAnimation(rawMoveX - viewDownX, MyApplication.getScreenWidthPx().toFloat())
                } else {
                    // 從移動的點回彈到邊界上
                    startAnimation(rawMoveX - viewDownX, 0f)
                }
                // 如果用戶移動了手指，那麼就攔截本次觸摸事件，從而不讓點擊事件生效
                return isTouchMove(viewDownX, event.x)
            }
        }
        return false
    }

    private fun startAnimation(startX: Float, endX: Float) {
        val animator = ValueAnimator.ofFloat(startX, endX)
        animator.duration = ((endX - startX).absoluteValue / 2).toLong()
        animator.addUpdateListener { animation -> updateLocation(animation.animatedValue as Float) }
        animator.start()
    }

    private fun updateLocation(x: Float) {
        targetView?.get()?.also { view ->
            if (view.translationX != x) {
                view.translationX = x
            }
        }
    }

    private fun isTouchMove(downX: Float, upX: Float): Boolean {
        return downX != upX
    }
}