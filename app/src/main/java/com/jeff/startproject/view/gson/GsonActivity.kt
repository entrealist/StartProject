package com.jeff.startproject.view.gson

import android.os.Bundle
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jeff.startproject.MyApplication
import com.jeff.startproject.R
import com.jeff.startproject.databinding.ActivityGsonBinding
import com.log.JFLog
import com.view.base.BaseActivity
import java.io.BufferedReader
import java.io.InputStreamReader

class GsonActivity : BaseActivity<ActivityGsonBinding>() {
    override fun getViewBinding(): ActivityGsonBinding = ActivityGsonBinding.inflate(layoutInflater)

    private val gson by lazy { Gson() }

    // 序列化有Expose
    private val exposeGson by lazy { GsonBuilder().excludeFieldsWithoutExposeAnnotation().create() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val inputStream = MyApplication.applicationContext().resources.openRawResource(R.raw.employee)
        val reader = BufferedReader(InputStreamReader(inputStream))
        val text = reader.readText()
        reader.close()
        inputStream.close()

        run {
            // val employee = gson.fromJson(text, Employee::class.java)
            // JFLog.d("$employee")
            // binding.tv.text = "$employee"
        }

        run {
            val employee = exposeGson.fromJson(text, Employee::class.java)
            JFLog.d("$employee")
            binding.tv.text = "$employee"
        }
    }
}