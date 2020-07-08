package com.jeff.startproject.view.file

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.jeff.startproject.R
import com.jeff.startproject.databinding.FragmentFileContentBinding
import com.jeff.startproject.enums.ModelResult
import com.jeff.startproject.view.base.ProgressFragment
import com.jeff.startproject.view.diaglog.ConfirmDialogFragment
import com.log.JFLog
import com.view.base.NavigateItem

class FileContentFragment : ProgressFragment<FragmentFileContentBinding, FileContentViewModel>() {

    val viewModel: FileContentViewModel by viewModels()

    override fun fetchViewModel() = viewModel

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentFileContentBinding =
        FragmentFileContentBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner) {
            // Handle the back button event
            ConfirmDialogFragment(getString(R.string.message_back_disable), false).show(
                parentFragmentManager,
                "Confirm"
            )
        }

        viewModel.status.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.also { result ->
                when (result) {
                    is ModelResult.Success -> {
                        result.data
                    }
                    is ModelResult.Failure -> {
                        result.throwable.localizedMessage
                    }
                    else -> null
                }?.also {
                    binding.tvContent.text = it
                }

                when (result) {
                    is ModelResult.Success -> R.color.silver
                    is ModelResult.Failure -> R.color.orange_red
                    ModelResult.Loading -> R.color.black
                    else -> null
                }?.also {
                    binding.viewStatus.setBackgroundColor(resources.getColor(it, null))
                }
            }
        })

        arguments?.getString("path")?.also {
            viewModel.readFile(it, true)
        }

        binding.toolbar.setNavigationOnClickListener {
            navigateTo(NavigateItem.Up)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Attach activity
        JFLog.d("onAttach: $context")
    }

    override fun onDetach() {
        super.onDetach()

        JFLog.d("onDetach")
    }
}