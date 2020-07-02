package com.view.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.viewbinding.ViewBinding

abstract class BaseDialogFragment<out B : ViewBinding> : AppCompatDialogFragment() {

    abstract fun isFullLayout(): Boolean

    private var _viewBinding: B? = null
    val binding: B get() = _viewBinding!!

    abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): B

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return getViewBinding(inflater, container).run {
            _viewBinding = this
            root
        }
    }

    override fun onStart() {
        super.onStart()

        dialog?.window?.also { window ->
            if (isFullLayout()) {
                window.setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT
                )
                window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            } else {
                val widthPixels = (resources.displayMetrics.widthPixels * 0.8).toInt()
                window.setLayout(widthPixels, ViewGroup.LayoutParams.WRAP_CONTENT)
                window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
        }
    }
}