package com.jeff.startproject.view.edittext

import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import com.jeff.startproject.databinding.ActivityEditBinding
import com.jeff.startproject.view.base.BaseActivity
import com.log.JFLog

// (TextWatcher) https://developer.android.com/reference/android/text/TextWatcher.html

/**
 * beforeTextChanged:
 *
 * This method is called to notify you that, within s, the count characters beginning at start are about to be replaced by new text with length after.
 * It is an error to attempt to make changes to s from this callback.
 *
 */

/**
 * onTextChanged:
 *
 * This method is called to notify you that, within s, the count characters beginning at start have just replaced old text that had length before.
 * It is an error to attempt to make changes to s from this callback.
 *
 */

/**
 * afterTextChanged:
 *
 * This method is called to notify you that, somewhere within s, the text has been changed.
 * It is legitimate to make further changes to s from this callback, but be careful not to get yourself into an infinite loop, because any changes you make will cause this method to be called again recursively.
 * (You are not told where the change took place because other afterTextChanged() methods may already have made other changes and invalidated the offsets.
 * But if you need to know here, you can use Spannable#setSpan in onTextChanged(CharSequence, int, int, int) to mark your place and then look up from here where the span ended up.
 *
 */

class EditTextActivity : BaseActivity<ActivityEditBinding>() {

    override fun getViewBinding(): ActivityEditBinding {
        return ActivityEditBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (true) {
            //MyTextWatcher(binding.edit1)
            CurrencyTextWatcher(binding.edit1)
        } else {
            binding.edit1.addTextChangedListener(
                beforeTextChanged = { s, start, count, after ->
                    JFLog.d("beforeTextChanged: $s, start: $start, count: $count, after: $after")
                    // 2位+1: beforeTextChanged: 12, start: 0, count: 2, after: 3
                    // 2位-1: beforeTextChanged: 12, start: 1, count: 1, after: 0
                },
                onTextChanged = { s, start, before, count ->
                    JFLog.d("onTextChanged: $s, start: $start, before: $before, count: $count")
                    // 2位+1: onTextChanged: 123, start: 0, before: 2, count: 3
                    // 2位-1: onTextChanged: 1, start: 1, before: 1, count: 0
                },
                afterTextChanged = { editable ->
                    JFLog.d("afterTextChanged: ${editable.toString()}")
                    // 2位+1: afterTextChanged: 123
                    // 2位-1: afterTextChanged: 1
                }
            )
        }
    }
}