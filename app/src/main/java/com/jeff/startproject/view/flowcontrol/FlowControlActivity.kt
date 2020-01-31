package com.jeff.startproject.view.flowcontrol

import android.os.Bundle
import com.jeff.startproject.R
import com.jeff.startproject.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_flow_control.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FlowControlActivity : BaseActivity() {

    private val viewModel by viewModel<FlowControlViewModel>()

    override fun getLayoutId(): Int {
        return R.layout.activity_flow_control
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btn_cancel.setOnClickListener {
            viewModel.tryCancelPreviousThenRun()
        }

        btn_cancel_task_1.setOnClickListener {
            viewModel.cancelTask()
        }

        btn_join.setOnClickListener {
            viewModel.tryJoinPreviousOrRun()
        }

        btn_cancel_task_2.setOnClickListener {
            viewModel.cancelTask()
        }
    }
}