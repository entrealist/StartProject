package com.jeff.startproject.view.file

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.jeff.startproject.R
import com.jeff.startproject.databinding.FragmentFileContentBinding
import com.jeff.startproject.enums.ModelResult
import com.log.JFLog
import com.view.base.BaseFragment
import com.view.base.NavigateItem
import org.koin.androidx.viewmodel.ext.android.viewModel

class FileContentFragment : BaseFragment<FragmentFileContentBinding>() {

    val viewModel by viewModel<FileContentViewModel>()

    override fun fetchViewModel() = viewModel

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentFileContentBinding =
        FragmentFileContentBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.result.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is ModelResult.Success -> {
                    result.data
                }
                is ModelResult.Failure -> {
                    result.data
                }
                else -> null
            }?.also {
                binding.tvContent.text = it
            }

            when (result) {
                is ModelResult.Success -> R.color.silver
                is ModelResult.Failure -> R.color.orange_red
                ModelResult.Progressing -> R.color.pale_green
                else -> 0
            }.also {
                binding.viewStatus.setBackgroundColor(resources.getColor(it, null))
            }
        })

        arguments?.getString("path")?.also {
            viewModel.readFile(it, true)
        }

        binding.toolbar.setNavigationOnClickListener {
            viewModel.navigateTo(NavigateItem(0, null))
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