package com.jeff.startproject.view.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

abstract class LogcatViewModel : BaseViewModel() {
    private val mRecordLog = MutableLiveData<String>()
    val recordLog: LiveData<String> = mRecordLog

    private val mStringBuilder = StringBuilder()

    fun appendMessage(message: String?) {
        if (message != null) {
            mStringBuilder.appendln(message)
            mRecordLog.value = mStringBuilder.toString()
        }
    }
}