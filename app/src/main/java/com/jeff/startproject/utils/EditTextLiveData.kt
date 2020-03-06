package com.jeff.startproject.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.lifecycle.LiveData
import java.lang.ref.WeakReference

abstract class EditTextLiveData(value: String? = null) : LiveData<String>(value) {
    private var isFirst = false
    private var editText: EditText? = null
    private var ref: WeakReference<EditTextLiveData>? = null

    private val textWatcher: TextWatcher by lazy {
        object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (isFirst) {
                    isFirst = false
                } else {
                    ref?.get()?.setLiveDataValue(s.toString())
                }
            }

            override fun afterTextChanged(editable: Editable?) {}
        }
    }

    private fun setLiveDataValue(value: String?) {
        super.setValue(value)
    }

    var bindingEditText: EditText?
        get() {
            return editText
        }
        set(view) {
            if (ref?.get() == null) {
                ref = WeakReference(this)
            }

            if (editText != null) {
                editText?.removeTextChangedListener(textWatcher)
                editText = null
            }

            if (view != null) {
                isFirst = true
                editText = view
                view.addTextChangedListener(textWatcher)
                value.also {
                    view.setText(it ?: "")
                    view.setSelection(it?.length ?: 0)
                }
            }
        }

    override fun setValue(value: String?) {
        when (editText) {
            null -> super.setValue(value)
            else -> editText?.setText(value ?: "")
        }
    }

    override fun postValue(value: String?) {
        when (editText) {
            null -> super.postValue(value)
            else -> editText?.setText(value ?: "")
        }
    }
}