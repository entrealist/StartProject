package com.jeff.startproject.view.text

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ImageSpan
import com.jeff.startproject.R
import com.jeff.startproject.databinding.ActivityTextBinding
import com.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_text.*

class TextActivity : BaseActivity<ActivityTextBinding>() {
    override fun getViewBinding(): ActivityTextBinding {
        return ActivityTextBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val stringBuilder = SpannableStringBuilder("0123456789".let {
            val sb = StringBuilder()
            repeat(10) { _ ->
                sb.append(it)
            }
            sb.toString()
        })

        var i = 0
        while (i >= 0) {
            if (i > 0) {
                i++
            }

            i = stringBuilder.indexOf("5", i, false)
            if (i >= 0) {
                stringBuilder.setSpan(ImageSpan(this, R.drawable.ic_smile), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }

        tv1.text = stringBuilder
    }
}