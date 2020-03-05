package com.jeff.startproject.utils

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.MutableLiveData

class LiveDataTextWatcher(private val liveData: MutableLiveData<String>) : TextWatcher {

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

    override fun afterTextChanged(editable: Editable?) {
        val string = editable.toString()
        if (liveData.value != string) {
            liveData.value = string
        }
    }
}