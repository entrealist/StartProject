package com.jeff.startproject.view.eventbus

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.jeff.startproject.databinding.ActivityEventBusBinding
import com.jeremyliao.liveeventbus.LiveEventBus
import com.log.JFLog
import com.view.base.BaseActivity
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

/*
 * https://github.com/JeremyLiao/LiveEventBus
 *
 * https://material.io/develop/android/components/material-text-view/
 */

class LiveEventBusActivity : BaseActivity<ActivityEventBusBinding>() {

    companion object {
        const val KEY_ARRAY = "array"
        const val KEY_STRING = "string"
        const val KEY_INT = "Int"
        const val KEY_CUSTOMIZE = "customize"
        const val KEY_SELF = "self"
        const val KEY_DELAY = "delay"
        const val KEY_STICKY = "sticky"
    }

    private val viewModel: EventBusViewModel by viewModels()

    override fun getViewBinding(): ActivityEventBusBinding {
        return ActivityEventBusBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnNext.setOnClickListener {
            Intent(this, LiveEventBusActivity::class.java).also {
                startActivity(it)
            }
        }

        LiveEventBus
            .get(KEY_ARRAY, Array<String>::class.java)
            .observe(this, Observer { array ->
                val sb = StringBuilder()

                array.forEach {
                    sb.append("$it, ")
                }

                binding.editCommon.setText(sb.toString())
            })

        LiveEventBus
            .get(KEY_STRING, String::class.java)
            .observe(this, Observer { string ->
                binding.editCommon.setText(string)
            })

        LiveEventBus
            .get(KEY_INT, Int::class.java)
            .observe(this, Observer { int ->
                binding.editCommon.setText("$int")
            })

        LiveEventBus
            .get(KEY_CUSTOMIZE, UserData::class.java)
            .observe(this, Observer { ver ->
                binding.editCommon.setText(ver.name)
            })

        LiveEventBus
            .get(KEY_SELF, LiveEventBusActivity::class.java)
            .observe(this, Observer { activity ->
                val code = "$this".substringAfterLast("@")
                val receivedCoe = "$activity".substringAfterLast("@")
                "$code : $receivedCoe".also {
                    JFLog.d(it)
                    binding.editCommon.setText(it)
                }
            })

        LiveEventBus
            .get(KEY_DELAY, String::class.java)
            .observe(this, Observer { string ->
                binding.editCommon.setText(string)
            })

        LiveEventBus
            .get(KEY_STICKY, String::class.java)
            .observeSticky(this, Observer { string ->
                val code = "$this".substringAfterLast("@")

                JFLog.d("$code : $string")

                binding.editSticky.setText(string)
            })

        binding.btnSend.setOnClickListener {
            //sendArray()
            sendString()
            //sendInt()
            //sendCustomize()

            //sendSelf()

            //sendStringDelay()

            sendStringSticky()
        }
    }

    private fun sendArray() {
        val arrayList = ArrayList<String>()
        arrayList.add("a")
        arrayList.add("b")
        arrayList.add("c")
        arrayList.add("d")
        arrayList.add("e")
        arrayList.add("f")

        LiveEventBus
            .get(KEY_ARRAY)
            .post(arrayList.toTypedArray())
    }

    private fun sendString() {
        LiveEventBus
            .get(KEY_STRING)
            .post("${Random(Calendar.getInstance().timeInMillis).nextInt()}")
    }

    private fun sendInt() {
        LiveEventBus
            .get(KEY_INT)
            .post(Random(Calendar.getInstance().timeInMillis).nextInt())
    }

    private fun sendCustomize() {
        LiveEventBus
            .get(KEY_CUSTOMIZE)
            .post(UserData("Jeff"))
    }

    private fun sendSelf() {
        LiveEventBus
            .get(KEY_SELF)
            .post(this)
    }

    private fun sendStringDelay() {
        LiveEventBus
            .get(KEY_DELAY)
            .postDelay("${Random(Calendar.getInstance().timeInMillis).nextInt()}", 5000L)
    }

    /**
     * Sticky: 註冊時會收到最後發送的消息
     */
    private fun sendStringSticky() {
        LiveEventBus
            .get(KEY_STICKY)
            .post("${Random(Calendar.getInstance().timeInMillis).nextInt()}")
    }
}