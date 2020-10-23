package com.jeff.startproject.widget.floating

import android.content.Context
import kotlinx.android.synthetic.main.view_floating_message.view.*

class StringFloatingMessageToast private constructor(context: Context) : FloatingMessageToast<String>(context) {

    companion object {
        fun builder(context: Context) = StringFloatingMessageToast(context)
    }

    override fun updateContentToView(content: String?, view: DraggableView) {
        view.btn.setOnClickListener {
            callback?.onCloseToast(view)
        }
    }
}