package com.jeff.startproject.view.flowcontrol

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.jeff.startproject.databinding.ActivityFlowControlBinding
import com.view.base.BaseActivity

class FlowControlActivity : BaseActivity<ActivityFlowControlBinding>() {

    private val viewModel: FlowControlViewModel by viewModels()

    override fun getViewBinding(): ActivityFlowControlBinding {
        return ActivityFlowControlBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.recordLog.observe(this, Observer { message ->
            binding.textLog.text = message
        })

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