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
                    // +1: beforeTextChanged: 123456789, start: 4, count: 0, after: 1
                    // -1: beforeTextChanged: 123456789, start: 3, count: 1, after: 0

                    // -5: beforeTextChanged: 123456789, start: 2, count: 5, after: 0
                    // -5+1: beforeTextChanged: 123456789, start: 2, count: 5, after: 1
                },
                onTextChanged = { s, start, before, count ->
                    JFLog.d("onTextChanged: $s, start: $start, before: $before, count: $count")
                    // +1: onTextChanged: 1234056789, start: 4, before: 2, count: 1
                    // -1: onTextChanged: 12356789, start: 3, before: 1, count: 0

                    // -5: onTextChanged: 1289, start: 2, before: 5, count: 0
                    // -5+1: onTextChanged: 12089, start: 2, before: 5, count: 1

                },
                afterTextChanged = { editable ->
                    JFLog.d("afterTextChanged: ${editable.toString()}")
                    // +1: afterTextChanged: 1234056789
                    // -1: afterTextChanged: 12356789

                    // -5: afterTextChanged: 1289
                    // -5+1: afterTextChanged: 12089
                }
            )
        }
    }
}