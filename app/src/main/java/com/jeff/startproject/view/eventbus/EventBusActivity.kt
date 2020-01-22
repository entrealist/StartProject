package com.jeff.startproject.view.eventbus

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.jeff.startproject.R
import com.jeff.startproject.view.base.BaseActivity
import com.jeremyliao.liveeventbus.LiveEventBus
import com.log.JFLog
import kotlinx.android.synthetic.main.activity_event_bus.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

/*
 * https://github.com/JeremyLiao/LiveEventBus
 *
 * https://material.io/develop/android/components/material-text-view/
 */

class EventBusActivity : BaseActivity() {

    private val viewModel by viewModel<EventBusViewModel>()

    override fun getLayoutId(): Int {
        return R.layout.activity_event_bus
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btn_next.setOnClickListener {
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

                edit_1.setText(sb.toString())
            })


        btn_send.setOnClickListener {
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
                edit_1.setText(string)
            })


        btn_send.setOnClickListener {
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
                edit_1.setText("$int")
            })


        btn_send.setOnClickListener {
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
                edit_1.setText(ver.name)
            })


        btn_send.setOnClickListener {
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
                    edit_1.setText(it)
                }

            })

        btn_send.setOnClickListener {
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
                edit_1.setText(string)
            })


        btn_send.setOnClickListener {
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

                edit_1.setText(string)
            })

        btn_send.setOnClickListener {
            LiveEventBus
                .get(key)
                .post("${Random(Calendar.getInstance().timeInMillis).nextInt()}")
        }
    }
}