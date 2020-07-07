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

class EventBusActivity : BaseActivity<ActivityEventBusBinding>() {

    private val viewModel: EventBusViewModel by viewModels()

    override fun getViewBinding(): ActivityEventBusBinding {
        return ActivityEventBusBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnNext.setOnClickListener {
            Intent(this, EventBusActivity::class.java).also {
                startActivity(it)
            }
        }

        //tryArray()
        //tryString()
        //tryInt()
        //tryCustomize()

        //trySelf()

        //tryStringDelay()

        tryStringSticky()
    }

    private fun tryArray() {
        val key = "array"

        LiveEventBus
            .get(key, Array<String>::class.java)
            .observe(this, Observer { array ->
                val sb = StringBuilder()

                array.forEach {
                    sb.append("$it, ")
                }

                binding.edit1.setText(sb.toString())
            })


        binding.btnSend.setOnClickListener {
            val arrayList = ArrayList<String>()
            arrayList.add("a")
            arrayList.add("b")
            arrayList.add("c")
            arrayList.add("d")
            arrayList.add("e")
            arrayList.add("f")

            LiveEventBus
                .get(key)
                .post(arrayList.toTypedArray())
        }
    }

    private fun tryString() {
        val key = "string"

        LiveEventBus
            .get(key, String::class.java)
            .observe(this, Observer { string ->
                binding.edit1.setText(string)
            })


        binding.btnSend.setOnClickListener {
            LiveEventBus
                .get(key)
                .post("${Random(Calendar.getInstance().timeInMillis).nextInt()}")
        }
    }

    private fun tryInt() {
        val key = "int"

        LiveEventBus
            .get(key, Int::class.java)
            .observe(this, Observer { int ->
                binding.edit1.setText("$int")
            })


        binding.btnSend.setOnClickListener {
            LiveEventBus
                .get(key)
                .post(Random(Calendar.getInstance().timeInMillis).nextInt())
        }
    }

    private fun tryCustomize() {
        val key = "customize"

        LiveEventBus
            .get(key, UserData::class.java)
            .observe(this, Observer { ver ->
                binding.edit1.setText(ver.name)
            })


        binding.btnSend.setOnClickListener {
            LiveEventBus
                .get(key)
                .post(UserData("Jeff"))
        }
    }

    private fun trySelf() {
        val key = "self"

        LiveEventBus
            .get(key, EventBusActivity::class.java)
            .observe(this, Observer { activity ->
                val code = "$this".substringAfterLast("@")
                val receivedCoe = "$activity".substringAfterLast("@")

                "$code : $receivedCoe".also {
                    JFLog.d(it)
                    binding.edit1.setText(it)
                }

            })

        binding.btnSend.setOnClickListener {
            LiveEventBus
                .get(key)
                .post(this)
        }
    }

    private fun tryStringDelay() {
        val key = "string"

        LiveEventBus
            .get(key, String::class.java)
            .observe(this, Observer { string ->
                binding.edit1.setText(string)
            })


        binding.btnSend.setOnClickListener {
            LiveEventBus
                .get(key)
                .postDelay("${Random(Calendar.getInstance().timeInMillis).nextInt()}", 5000L)
        }
    }

    /**
     * Sticky: 註冊時會收到最後發送的消息
     */
    private fun tryStringSticky() {
        val key = "string"

        LiveEventBus
            .get(key, String::class.java)
            .observeSticky(this, Observer { string ->
                val code = "$this".substringAfterLast("@")

                JFLog.d("$code : $string")

                binding.edit1.setText(string)
            })

        binding.btnSend.setOnClickListener {
            LiveEventBus
                .get(key)
                .post("${Random(Calendar.getInstance().timeInMillis).nextInt()}")
        }
    }
}