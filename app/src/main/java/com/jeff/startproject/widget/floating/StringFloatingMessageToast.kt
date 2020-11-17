package com.jeff.startproject.widget.floating

import android.content.Context
import android.view.View
import com.jeff.startproject.R

class StringFloatingMessageToast constructor(context: Context) : FloatingMessageToast<String>(context) {

    override fun updateContentToView(content: String?, view: DraggableView) {
        view.findViewById<View>(R.id.btn).setOnClickListener {
            callback?.onCloseToast(view)
        }
    }
}