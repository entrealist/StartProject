package com.jeff.startproject.widget.floating

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import com.jeff.startproject.R
import kotlin.math.absoluteValue

abstract class FloatingMessageToast<T>(private val context: Context) {

    interface FloatingMessageToastCallback {
        fun onCloseToast(view: View)
    }

    private val handler = Handler(Looper.getMainLooper())

    private val screenWidthPx by lazy {
        context.resources.displayMetrics.widthPixels
    }

    private val screenHeightPx by lazy {
        context.resources.displayMetrics.heightPixels
    }

    abstract fun updateContentToView(content: T?, view: DraggableView)

    protected var callback: FloatingMessageToastCallback? = null
        private set

    fun setCallback(obj: FloatingMessageToastCallback) = apply {
        this.callback = obj
    }

    private var content: T? = null
    fun setContent(content: T) = apply {
        this.content = content
    }

    private var duration = 0L
    fun setDuration(long: Long) = apply {
        this.duration = long
    }

    fun show(): View = DraggableView(context).also {
        updateContentToView(content, it)
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        windowManager.addView(it, getWindowLayoutParams())
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
        y = (screenHeightPx * .5f).toInt()
    }

    inner class DraggableView @JvmOverloads constructor(context: Context) : FrameLayout(context) {
        init {
            inflate(context, R.layout.view_floating_message, this)
        }

        private var viewDownX = 0f
        private var isSwipeOut = false
        private var isTimesUp = false

        override fun onTouchEvent(event: MotionEvent): Boolean {
            if (isSwipeOut || isTimesUp)
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
                    if (rawMoveX - viewDownX > screenWidthPx / 3) {
                        isSwipeOut = true
                        val endX = screenWidthPx.toFloat()
                        startAnimation(rawMoveX - viewDownX, endX) {
                            if (isSwipeOut && it == endX) {
                                visibility = View.INVISIBLE
                                callback?.onCloseToast(this)
                            }
                        }
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

        private fun startAnimation(startX: Float, endX: Float, closure: ((Float) -> Unit)? = null) {
            val animator = ValueAnimator.ofFloat(startX, endX)
            animator.duration = ((endX - startX).absoluteValue / 2).toLong()
            animator.addUpdateListener { animation ->
                (animation.animatedValue as Float).also {
                    updateLocation(it)
                    closure?.invoke(it)
                }
            }
            animator.start()
        }

        private fun isTouchMove(downX: Float, upX: Float): Boolean {
            return downX != upX
        }

        private fun updateLocation(x: Float) {
            if (translationX != x) {
                alpha = 1 - x / screenWidthPx
                translationX = x
            }
        }

        fun timesUp() {
            isTimesUp = true

            val animator = ValueAnimator.ofFloat(1f, 0f)
            animator.duration = 200
            animator.addUpdateListener { animation ->
                alpha = animation.animatedValue as Float
            }
            animator.start()
        }
    }
}