package com.jeff.startproject.view.draw

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class ArcView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var cornerSize = 0f
    private val paint = Paint()

    init {
        paint.color = Color.YELLOW
        paint.isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        cornerSize = (width / 2).toFloat()

        canvas?.also {
            drawLeftTop(canvas)
            drawRightTop(canvas)
            drawLeftBottom(canvas)
            drawRightBottom(canvas)
        }
    }

    private fun drawLeftTop(canvas: Canvas) {
        val path = Path()
        path.moveTo(0f, cornerSize)
        path.lineTo(0f, 0f)
        path.lineTo(cornerSize, 0f)
        path.arcTo(RectF(0f, 0f, cornerSize * 2, cornerSize * 2), -90f, -90f)
        path.close()
        canvas.drawPath(path, paint)
    }

    private fun drawLeftBottom(canvas: Canvas) {
        val path = Path()
        path.moveTo(0f, height - cornerSize)
        path.lineTo(0f, height.toFloat())
        path.lineTo(cornerSize, height.toFloat())
        path.arcTo(RectF(0f, height - cornerSize * 2, cornerSize * 2, height.toFloat()), 90f, 90f)
        path.close()
        canvas.drawPath(path, paint)
    }

    private fun drawRightBottom(canvas: Canvas) {
        val path = Path()
        path.moveTo(width.toFloat() - cornerSize, height.toFloat())
        path.lineTo(width.toFloat(), height.toFloat())
        path.lineTo(width.toFloat(), height - cornerSize)
        val oval = RectF(
            width.toFloat() - cornerSize * 2, height
                    - cornerSize * 2, width.toFloat(), height.toFloat()
        )
        path.arcTo(oval, 0f, 90f)
        path.close()
        canvas.drawPath(path, paint)
    }

    private fun drawRightTop(canvas: Canvas) {
        val path = Path()
        path.moveTo(width.toFloat(), cornerSize)
        path.lineTo(width.toFloat(), 0f)
        path.lineTo(width.toFloat() - cornerSize, 0f)
        path.arcTo(
            RectF(
                width.toFloat() - cornerSize * 2, 0f, width.toFloat(),
                0 + cornerSize * 2
            ), -90f, 90f
        )
        path.close()
        canvas.drawPath(path, paint)
    }
}