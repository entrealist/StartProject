package com.jeff.startproject.utils

import android.graphics.drawable.Animatable2
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView

fun Drawable.repeatAnimation(view: View) {
    val anim = this as? AnimatedVectorDrawable
    if (false == anim?.isRunning) {
        anim.registerAnimationCallback(object : Animatable2.AnimationCallback() {
            override fun onAnimationEnd(drawable: Drawable?) {
                super.onAnimationEnd(drawable)

                // Api 24 以下需要在主執行緒啟動
                view.post {
                    anim.start()
                }
            }
        })

        anim.start()
    }
}

fun Drawable.stopAnimation() {
    val anim = this as? AnimatedVectorDrawable
    if (true == anim?.isRunning) {
        anim.stop()
    }
}

fun ImageView.repeatAnimation() {
    this.drawable.repeatAnimation(this)
}

fun ImageView.stopAnimation() {
    this.drawable.stopAnimation()
}