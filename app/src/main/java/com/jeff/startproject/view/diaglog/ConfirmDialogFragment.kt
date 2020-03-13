package com.jeff.startproject.view.diaglog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jeff.startproject.databinding.DialogConfirmBinding
import com.view.base.BaseDialogFragment

class ConfirmDialogFragment(
    private val initMessage: String? = null,
    private val initCancelable: Boolean = false) : BaseDialogFragment<DialogConfirmBinding>() {

    override fun isFullLayout() = false

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): DialogConfirmBinding {
        return DialogConfirmBinding.inflate(inflater, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        isCancelable = initCancelable
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when {
            initMessage != null -> initMessage
            else -> arguments?.getString("message")
        }?.also {
            binding.tvMessage.text = it
        }

        binding.btnConfirm.setOnClickListener {
            this.dismiss()
        }
    }
}