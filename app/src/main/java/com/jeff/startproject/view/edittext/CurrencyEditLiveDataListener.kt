package com.jeff.startproject.view.edittext

import com.jeff.startproject.utils.livedata.EditTextLiveDataListener
import com.jeff.startproject.utils.livedata.TextWatcherUpdated
import java.lang.StringBuilder
import com.log.JFLog

class CurrencyEditLiveDataListener : EditTextLiveDataListener {
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

    override fun onTextChanged(
        s: CharSequence?,
        start: Int,
        before: Int,
        count: Int
    ): TextWatcherUpdated? {
        val startTime = System.currentTimeMillis()

        var selectionSupport = 0

        val cleanString =
            s.toString().let {
                when {
                    isRemoveDot -> {
                        selectionSupport--
                        isRemoveDot = false
                        it.removeRange(start - 1, start)
                    }
                    else -> it
                }
            }.run {
                replace(",", "")
            }.let {
                val dotIndex = it.lastIndexOfDot
                // 只允許小數點後兩位
                if (dotIndex > -1 && it.length - dotIndex > 3) {
                    it.substring(0, dotIndex + 3)
                } else {
                    it
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

        when {
            s != null && !resultString.contentEquals(s) -> {
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

                TextWatcherUpdated(lastString, newSelectionStart)
            }
            else -> null
        }.also {
            JFLog.d("Spend: ${System.currentTimeMillis() - startTime} ns")
            return it
        }
    }

    private val String.lastIndexOfDot: Int
        get() = this.lastIndexOf(".")
}