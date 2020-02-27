package com.jeff.startproject.view.edittext

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.lang.StringBuilder

// val moneyFormat = NumberFormat.getCurrencyInstance(Locale.CHINA).format(money)
class CurrencyTextWatcher(private val editText: EditText) : TextWatcher {

    init {
        editText.addTextChangedListener(this)
    }

    private var last = ""
    private var isUpdated = false
    private var isRemoveDot = false

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        if (isUpdated)
            return

        if (count == 1 && after == 0) {
            if (s != null && s.length % 4 == start % 4) {
                //if (!s.isNullOrBlank() && s[start] == ',') {
                isRemoveDot = true
            }
        }
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (s.toString() != last) {
            var selectionSupport = 0

            val nowString = when {
                isRemoveDot -> {
                    selectionSupport--
                    isRemoveDot = false
                    s.toString().removeRange(editText.selectionStart - 1, editText.selectionStart)
                }
                else -> s.toString()
            }

            val cleanString = nowString.replace(",", "").let { newString ->
                val dotIndex = newString.lastIndexOfDot
                // 只允許小數點後兩位
                if (dotIndex > -1 && newString.length - dotIndex > 3) {
                    newString.substring(0, dotIndex + 3)
                } else {
                    newString
                }
            }

            val startIndex = when (val dotIndex = cleanString.lastIndexOfDot) {
                -1 -> cleanString.length
                else -> dotIndex
            }

            val sb = StringBuilder(cleanString)

            if (startIndex > 3) {
                for (i in startIndex - 3 downTo 1 step 3) {
                    sb.insert(i, ",")
                }
            }

            val sbLength = sb.length

            // TODO: 未完成範圍刪除判斷游標
            val newSelectionStart =
                (sbLength - last.length)
                    .let {
                        when {
                            it > 1 -> editText.selectionStart + 1
                            it < -1 -> editText.selectionStart - 1
                            else -> editText.selectionStart
                        }
                    }.let {
                        it + selectionSupport
                    }.let {
                        when {
                            it < 0 -> 0
                            it > sbLength -> sbLength
                            else -> it
                        }
                    }

            isUpdated = true

            last = sb.toString()

            editText.setText(last)
            editText.setSelection(newSelectionStart)

            isUpdated = false
        }
    }

    override fun afterTextChanged(editable: Editable?) {
    }

    private val String.lastIndexOfDot: Int
        get() = this.lastIndexOf(".")
}