package com.jeff.startproject.view.flowcontrol

import android.os.Bundle
import com.jeff.startproject.databinding.ActivityFlowControlBinding
import com.jeff.startproject.view.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class FlowControlActivity : BaseActivity<ActivityFlowControlBinding>() {

    private val viewModel by viewModel<FlowControlViewModel>()

    override fun getViewBinding(): ActivityFlowControlBinding {
        return ActivityFlowControlBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnCancel.setOnClickListener {
            viewModel.tryCancelPreviousThenRun()
        }

        binding.btnCancelTask1.setOnClickListener {
            viewModel.cancelTask()
        }

        binding.btnJoin.setOnClickListener {
            viewModel.tryJoinPreviousOrRun()
        }

        binding.btnCancelTask2.setOnClickListener {
            viewModel.cancelTask()
        }
    }
}