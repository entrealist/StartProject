package com.jeff.startproject.utils.livedata

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.lifecycle.LiveData
import java.lang.ref.WeakReference

data class TextWatcherUpdated(val text: String, val selection: Int)

interface EditTextLiveDataListener {
    fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)
    fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int): TextWatcherUpdated?
}

abstract class PluginEditTextLiveData(value: String? = null) : LiveData<String>(value) {
    private var isFirst = false
    private var thisRef: WeakReference<PluginEditTextLiveData>? = null
    private var editTextRef: WeakReference<EditText>? = null
    private var liveDataListener: EditTextLiveDataListener? = null

    private val textWatcher: TextWatcher by lazy {
        object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                liveDataListener?.beforeTextChanged(s, start, count, after)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val listener = liveDataListener
                if (listener != null) {
                    editTextRef?.get()?.also { editText ->
                        val updated = listener.onTextChanged(s, start, before, count)
                        if (updated == null) {
                            if (!isFirst) {
                                thisRef?.get()?.setLiveDataValue(s.toString())
                            }
                        } else {
                            editText.removeTextChangedListener(this)
                            editText.setText(updated.text)
                            editText.setSelection(updated.selection)
                            editText.addTextChangedListener(this)

                            thisRef?.get()?.setLiveDataValue(updated.text)
                        }
                    }
                } else {
                    if (!isFirst) {
                        thisRef?.get()?.setLiveDataValue(s.toString())
                    }
                }

                if (isFirst) {
                    isFirst = false
                }
            }

            override fun afterTextChanged(editable: Editable?) {}
        }
    }

    private fun setLiveDataValue(value: String?) {
        super.setValue(value)
    }

    fun setTextListener(textLiveDataListener: EditTextLiveDataListener) {
        liveDataListener = textLiveDataListener
    }

    var bindingEditText: EditText?
        get() {
            return editTextRef?.get()
        }
        set(view) {
            if (thisRef?.get() == null) {
                thisRef = WeakReference(this)
            }

            editTextRef?.get()?.also { lastEdit ->
                lastEdit.removeTextChangedListener(textWatcher)
                editTextRef = null
            }

            view?.also {
                isFirst = true
                editTextRef = WeakReference(view)

                view.addTextChangedListener(textWatcher)
                value.also {
                    view.setText(it ?: "")
                    view.setSelection(it?.length ?: 0)
                }
            }
        }

    override fun setValue(value: String?) {
        when (val editText = editTextRef?.get()) {
            null -> super.setValue(value)
            else -> editText.setText(value ?: "")
        }
    }

    override fun postValue(value: String?) {
        when (val editText = editTextRef?.get()) {
            null -> super.postValue(value)
            else -> editText.setText(value ?: "")
        }
    }
}