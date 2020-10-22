package com.jeff.startproject.widget.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.jeff.startproject.MyApplication
import com.jeff.startproject.R
import kotlin.math.absoluteValue

class FloatingMessageToast private constructor(private val context: Context) {

    companion object {
        fun builder(context: Context) = FloatingMessageToast(context)
    }

    interface FloatingMessageToastCallback {
        fun onCloseToast(view: View)
    }

    private var callback: FloatingMessageToastCallback? = null
    fun setCallback(obj: FloatingMessageToastCallback) = apply {
        callback = obj
    }

    private var content: String? = null
    fun setContent(text: String) = apply {
        content = text
    }

    fun show(): View = DraggableView(context).apply {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.addView(this, getWindowLayoutParams())
    }

    private fun getWindowLayoutParams() = WindowManager.LayoutParams().apply {
        type = when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            else -> WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
        }
        flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        format = PixelFormat.TRANSLUCENT
        gravity = Gravity.TOP or Gravity.START
        width = WindowManager.LayoutParams.MATCH_PARENT
        height = WindowManager.LayoutParams.WRAP_CONTENT
        x = 0
        y = (MyApplication.getScreenHeightPx() * .5f).toInt()
    }

    inner class DraggableView @JvmOverloads constructor(context: Context) : FrameLayout(context) {
        init {
            inflate(context, R.layout.view_message, this)

            findViewById<ImageView>(R.id.btn).setOnClickListener {
                callback?.onCloseToast(this)
            }

            findViewById<TextView>(R.id.tv_top).text = content
        }

        private val screenWidthPx by lazy {
            MyApplication.getScreenWidthPx()
        }

        private var viewDownX = 0f
        private var isSwipeOut = false

        override fun onTouchEvent(event: MotionEvent): Boolean {
            if (isSwipeOut)
                return false

            val rawMoveX: Int
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    viewDownX = event.x
                }
                MotionEvent.ACTION_MOVE -> {
                    rawMoveX = event.rawX.toInt()
                    updateLocation(rawMoveX - viewDownX)
                }
                MotionEvent.ACTION_UP -> {
                    rawMoveX = event.rawX.toInt()
                    // 移動超過設定距離, 移出View.
                    if (rawMoveX - viewDownX > screenWidthPx / 2) {
                        isSwipeOut = true
                        startAnimation(rawMoveX - viewDownX, screenWidthPx.toFloat())
                    } else {
                        // 從移動的點回彈到邊界上
                        startAnimation(rawMoveX - viewDownX, 0f)
                    }
                    // 如果用戶移動了手指，就攔截本次觸摸事件，不讓點擊事件生效
                    return isTouchMove(viewDownX, event.x)
                }
            }
            return false
        }

        private fun startAnimation(startX: Float, endX: Float) {
            val animator = ValueAnimator.ofFloat(startX, endX)
            animator.duration = ((endX - startX).absoluteValue / 2).toLong()
            animator.addUpdateListener { animation ->
                (animation.animatedValue as Float).also {
                    updateLocation(it)
                    if (isSwipeOut && endX == animation.animatedValue) {
                        visibility = View.INVISIBLE
                        callback?.onCloseToast(this)
                    }
                }
            }
            animator.start()
        }

        private fun updateLocation(x: Float) {
            if (translationX != x) {
                alpha = 1 - x / screenWidthPx
                translationX = x
            }
        }

        private fun isTouchMove(downX: Float, upX: Float): Boolean {
            return downX != upX
        }
    }
}