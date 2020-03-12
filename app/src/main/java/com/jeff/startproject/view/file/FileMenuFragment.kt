package com.jeff.startproject.view.file

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.jeff.startproject.R
import com.jeff.startproject.databinding.FragmentFileMenuBinding
import com.jeff.startproject.view.base.BaseFragment
import com.jeff.startproject.view.base.NavigateItem
import com.jeff.startproject.view.diaglog.ConfirmDialogFragment
import com.log.JFLog
import kotlinx.android.synthetic.main.fragment_file_menu.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FileMenuFragment : BaseFragment<FragmentFileMenuBinding>() {

    val viewModel by viewModel<FileMenuViewModel>()

    override fun fetchViewModel() = viewModel

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentFileMenuBinding =
        FragmentFileMenuBinding.inflate(inflater, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.existActiveTask.observe(this, Observer {
            if (it) {
                ConfirmDialogFragment(getString(R.string.message_wait), false).show(parentFragmentManager, "Confirm")
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.processing.observe(viewLifecycleOwner, Observer {

        })

        viewModel.btnStartText.observe(viewLifecycleOwner, Observer {
            binding.btnStart.text = it
        })

        btn_start.setOnClickListener {
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

            viewModel.navigateTo(NavigateItem(R.id.action_file_menu_to_content, bundle))
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