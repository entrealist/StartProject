package com.jeff.startproject.view.base

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window.FEATURE_NO_TITLE
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.viewbinding.ViewBinding

abstract class BaseDialogFragment<B : ViewBinding> : AppCompatDialogFragment() {

    abstract fun isFullLayout(): Boolean

    private var mViewBinding: B? = null
    val binding: B get() = mViewBinding!!

    abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): B

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).also {
            it.window?.requestFeature(FEATURE_NO_TITLE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return getViewBinding(inflater, container).run {
            mViewBinding = this
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