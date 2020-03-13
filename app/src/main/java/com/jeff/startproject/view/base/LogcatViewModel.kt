package com.jeff.startproject.view.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.view.base.BaseViewModel

abstract class LogcatViewModel : BaseViewModel() {
    private val mRecordLog by lazy { MutableLiveData<String>() }
    val recordLog: LiveData<String> get() = mRecordLog

    private val mStringBuilder = StringBuilder()

    fun appendMessage(message: String?, isPost: Boolean = false) {
        if (message != null) {
            mStringBuilder.appendln(message)
            mStringBuilder.toString().also {
                if (isPost) {
                    mRecordLog.postValue(it)
                } else {
                    mRecordLog.value = it
                }
            }
        }
    }
}