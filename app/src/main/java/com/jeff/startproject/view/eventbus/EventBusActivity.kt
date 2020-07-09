package com.jeff.startproject.view.eventbus

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.jeff.startproject.databinding.ActivityEventBusBinding
import com.log.JFLog
import com.view.base.BaseActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

class EventBusActivity : BaseActivity<ActivityEventBusBinding>() {

    private val viewModel: EventBusViewModel by viewModels()


    override fun getViewBinding(): ActivityEventBusBinding {
        return ActivityEventBusBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        eventBusRegisterAuto()

        binding.btnNext.setOnClickListener {
            Intent(this, EventBusActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.btnSend.setOnClickListener {
            //sendArray()
            //sendString()
            //sendInt()
            //sendCustomize()

            //sendSelf()

            sendStringSticky()
        }


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun handleEventArray(array: Array<String>) {
        val sb = StringBuilder()

        array.forEach {
            sb.append("$it, ")
        }

        binding.editCommon.setText(sb.toString())
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun handleEventString(string: String) {
        binding.editCommon.setText(string)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun handleEventInt(int: Int) {
        binding.editCommon.setText("$int")
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun handleEventCustomize(userData: UserData) {
        binding.editCommon.setText(userData.name)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun handleEventSelf(activity: EventBusActivity) {
        val code = "$this".substringAfterLast("@")
        val receivedCoe = "$activity".substringAfterLast("@")
        "$code : $receivedCoe".also {
            JFLog.d(it)
            binding.editCommon.setText(it)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun handleEventSticky(string: String) {
        val code = "$this".substringAfterLast("@")

        JFLog.d("$code : $string")

        binding.editSticky.setText(string)
    }

    private fun sendArray() {
        val arrayList = ArrayList<String>()
        arrayList.add("a")
        arrayList.add("b")
        arrayList.add("c")
        arrayList.add("d")
        arrayList.add("e")
        arrayList.add("f")

        EventBus.getDefault()
            .post(arrayList.toTypedArray())
    }

    private fun sendString() {
        EventBus.getDefault()
            .post("${Random(Calendar.getInstance().timeInMillis).nextInt()}")
    }

    private fun sendInt() {
        EventBus.getDefault()
            .post(Random(Calendar.getInstance().timeInMillis).nextInt())
    }

    private fun sendCustomize() {
        EventBus.getDefault()
            .post(UserData("Jeff"))
    }

    private fun sendSelf() {
        EventBus.getDefault()
            .post(this)
    }

    /**
     * Sticky: 註冊時會收到最後發送的消息
     */
    private fun sendStringSticky() {
        EventBus.getDefault()
            .postSticky("${Random(Calendar.getInstance().timeInMillis).nextInt()}")
    }
}