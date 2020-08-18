package com.jeff.startproject.view.datastructure

import android.os.Bundle
import android.util.Log
import com.google.gson.internal.LinkedTreeMap
import com.jeff.startproject.databinding.ActivityDataStructureBinding
import com.log.JFLog
import com.view.base.BaseActivity
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.LinkedHashMap

class DataStructureActivity : BaseActivity<ActivityDataStructureBinding>() {

    override fun getViewBinding(): ActivityDataStructureBinding {
        return ActivityDataStructureBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val hashMap = HashMap<String, String>().also(::initMap)
        JFLog.d("$hashMap")

        // 依據插入順序
        val linkedHashMap = LinkedHashMap<String, String>().also(::initMap)
        JFLog.d("$linkedHashMap")

        val linkedTreeMap = LinkedTreeMap<String, String>().also(::initMap)
        JFLog.d("$linkedTreeMap")

        // 依據Key排序
        val treeMap = TreeMap<String, String>().also(::initMap)
        JFLog.d("$treeMap")
    }

    private fun initMap(map: MutableMap<String, String>) {
        map["5"] = "A"
        map["2"] = "B"
        map["3"] = "C"
        map["1"] = "D"
        map["4"] = "E"
    }
}