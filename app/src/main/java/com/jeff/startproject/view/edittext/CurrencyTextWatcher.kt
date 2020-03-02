package com.jeff.startproject.view.edittext

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.lang.StringBuilder
import com.log.JFLog

// val moneyFormat = NumberFormat.getCurrencyInstance(Locale.CHINA).format(money)
class CurrencyTextWatcher(private val editText: EditText) : TextWatcher {

    init {
        editText.addTextChangedListener(this)
    }

    // TODO: 不建議使用
    protected fun finalize() {
        JFLog.d("GC")
    }

    private var lastString = ""
    private var isRemoveDot = false

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        if (count == 1 && after == 0) {
            if (s != null && s.length % 4 == start % 4) {
                //if (!s.isNullOrBlank() && s[start] == ',') {
                isRemoveDot = true
            }
        }
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        val startTime = System.currentTimeMillis()

        var selectionSupport = 0

        val nowString = when {
            isRemoveDot -> {
                selectionSupport--
                isRemoveDot = false
                s.toString().removeRange(start - 1, start)
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

        val resultString = sb.toString()

        if (s != null && !resultString.contentEquals(s)) {
            editText.removeTextChangedListener(this)

            // TODO: 未完成範圍刪除判斷游標
            val sbLength = sb.length
            val newSelectionStart =
                (sbLength - lastString.length)
                    .let {
                        val startSel = start + count
                        when {
                            it > 1 -> startSel + 1
                            it < -1 -> startSel - 1
                            else -> startSel
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

            lastString = resultString

            editText.setText(lastString)
            editText.setSelection(newSelectionStart)

            editText.addTextChangedListener(this)
        }

        JFLog.d("Spend: ${System.currentTimeMillis() - startTime} ns")
    }

    override fun afterTextChanged(editable: Editable?) {
    }

    private val String.lastIndexOfDot: Int
        get() = this.lastIndexOf(".")
}