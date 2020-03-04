package com.jeff.startproject.view.vector

import android.graphics.drawable.Animatable2
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import com.jeff.startproject.databinding.ActivityVectorBinding
import com.jeff.startproject.view.base.BaseActivity
import com.log.JFLog
import kotlinx.android.synthetic.main.activity_vector.*

class VectorActivity : BaseActivity<ActivityVectorBinding>() {

    override fun getViewBinding(): ActivityVectorBinding {
        return ActivityVectorBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (iv_smile.drawable as AnimatedVectorDrawable).also { anim ->
            if (!anim.isRunning) {
                anim.registerAnimationCallback(object : Animatable2.AnimationCallback() {
                    override fun onAnimationEnd(drawable: Drawable?) {
                        super.onAnimationEnd(drawable)

                        // Api 24 以下需要在主執行緒啟動
                        iv_smile.post {
                            JFLog.d("Repeat")
                            anim.start()
                        }
                    }
                })

                JFLog.d("Start")
                anim.start()
            }
        }
    }
}