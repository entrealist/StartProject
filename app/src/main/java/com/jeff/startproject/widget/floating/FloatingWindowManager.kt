package com.jeff.startproject.widget.floating

import android.content.Context
import android.view.View
import android.view.WindowManager

object FloatingWindowManager {
    private val viewList = arrayListOf<View>()
    private val viewStateMap = mutableMapOf<Int, Boolean>()

    fun hideAllMessage(context: Context) {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        viewList.forEach {
            val isAdded = viewStateMap.getOrDefault(it.hashCode(), false)
            if (isAdded) {
                viewStateMap[it.hashCode()] = false
                windowManager.removeView(it)
            }
        }
    }

    fun clearAllMessage(context: Context) {
        hideAllMessage(context)
        viewList.clear()
        viewStateMap.clear()
    }

    fun clearSpecificView(context: Context, view: View) {
        viewList.find { it == view }?.also {
            val isAdded = viewStateMap.getOrDefault(it.hashCode(), false)
            if (isAdded) {
                val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                windowManager.removeView(it)

                viewStateMap.remove(it.hashCode())
                viewList.remove(it)
            }
        }
    }

    fun showAllFloating(context: Context) {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        viewList.forEach {
            val isAdded = viewStateMap.getOrDefault(it.hashCode(), false)
            if (!isAdded) {
                viewStateMap[it.hashCode()] = true
                windowManager.addView(it, it.layoutParams)
            }
        }
    }

    fun addFloating(view: View) {
        viewStateMap[view.hashCode()] = true
        viewList.add(view)
    }
}