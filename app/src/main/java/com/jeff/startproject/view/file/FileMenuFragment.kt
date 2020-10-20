package com.jeff.startproject.view.file

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.jeff.startproject.MyApplication
import com.jeff.startproject.R
import com.jeff.startproject.databinding.FragmentFileMenuBinding
import com.jeff.startproject.view.diaglog.ConfirmDialogFragment
import com.log.JFLog
import com.view.base.BaseFragment
import com.view.base.NavigateItem
import kotlinx.coroutines.launch

class FileMenuFragment : BaseFragment<FragmentFileMenuBinding>() {

    val viewModel: FileMenuViewModel by viewModels()

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentFileMenuBinding =
        FragmentFileMenuBinding.inflate(inflater, container, false)

    override fun navigateTo(item: NavigateItem) {
        lifecycleScope.launch {
            viewModel.joinPreviousOrRun {
                super.navigateTo(item)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewStatus.layoutParams.height = MyApplication.getStatusBarHeight()

        viewModel.existActiveTask.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.also {
                if (it) {
                    ConfirmDialogFragment(getString(R.string.message_wait), false).show(parentFragmentManager, "Confirm")
                }
            }
        })

        viewModel.request.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.also {
                JFLog.d("Request result: $it")
            }
        })

        viewModel.btnStartText.observe(viewLifecycleOwner, Observer {
            binding.btnStart.text = it
        })

        binding.btnStart.setOnClickListener {
            viewModel.start()
        }

        val clickListener = View.OnClickListener {
            val bundle = Bundle()

            when (it) {
                binding.btnOpen1 -> viewModel.dirPath
                binding.btnOpen2 -> viewModel.mvPath
                binding.btnOpen3 -> viewModel.rmPath
                else -> null
            }?.also { path ->
                bundle.putString("path", path)
            }

            navigateTo(NavigateItem.Destination(R.id.action_file_menu_to_content, bundle))
        }
        binding.btnOpen1.setOnClickListener(clickListener)
        binding.btnOpen2.setOnClickListener(clickListener)
        binding.btnOpen3.setOnClickListener(clickListener)

        binding.btnRest.setOnClickListener {
            viewModel.resetFiles()
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