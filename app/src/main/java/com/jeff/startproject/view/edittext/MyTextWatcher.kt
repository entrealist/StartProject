package com.jeff.startproject.view.edittext

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.log.JFLog

// https://kknews.cc/zh-tw/news/mg8bl89.html
class MyTextWatcher(private val editText: EditText) : TextWatcher {

    private var regex: Regex

    init {
        regex = Regex("^[A-Za-z@]+$")

        editText.addTextChangedListener(this)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        JFLog.d("onTextChanged")
    }

    override fun afterTextChanged(editable: Editable?) {
        editable?.also {
            if (needUpdated(it)) {
                editText.removeTextChangedListener(this)
                updatedEditable(it)
                editText.addTextChangedListener(this)
            }
        }
    }

    private fun needUpdated(editable: Editable): Boolean {
        return (!regex.matches(editable.toString())).also {
            JFLog.d("NeedUpdated: $it")
        }
    }

    private fun updatedEditable(editable: Editable) {
        editable.insert(editable.length, "@")
    }
}