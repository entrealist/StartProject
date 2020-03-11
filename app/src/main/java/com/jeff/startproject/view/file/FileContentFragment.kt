package com.jeff.startproject.view.file

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.jeff.startproject.databinding.FragmentFileContentBinding
import com.jeff.startproject.view.base.BaseFragment
import com.log.JFLog
import org.koin.androidx.viewmodel.ext.android.viewModel

class FileContentFragment : BaseFragment<FragmentFileContentBinding>() {

    val viewModel by viewModel<FileContentViewModel>()

    override fun fetchViewModel() = viewModel

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentFileContentBinding =
        FragmentFileContentBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.content.observe(viewLifecycleOwner, Observer {
            binding.tvContent.text = it
        })

        arguments?.getString("path")?.also {
            viewModel.readFile(it)
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