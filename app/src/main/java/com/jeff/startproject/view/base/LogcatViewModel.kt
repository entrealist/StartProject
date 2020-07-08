package com.jeff.startproject.view.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.view.base.BaseViewModel

abstract class LogcatViewModel : BaseViewModel() {
    private val _recordLog by lazy { MutableLiveData<String>() }
    val recordLog: LiveData<String> get() = _recordLog

    private val _stringBuilder = StringBuilder()

    fun appendMessage(message: String?, isPost: Boolean = false) {
        if (message != null) {
            _stringBuilder.appendln(message)
            _stringBuilder.toString().also {
                if (isPost) {
                    _recordLog.postValue(it)
                } else {
                    _recordLog.value = it
                }
            }
        }
    }
}