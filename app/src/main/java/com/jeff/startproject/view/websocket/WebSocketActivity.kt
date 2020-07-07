package com.jeff.startproject.view.websocket

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.jeff.startproject.BuildConfig
import com.jeff.startproject.R
import com.jeff.startproject.databinding.ActivityWebsocketBinding
import com.view.base.BaseActivity

class WebSocketActivity : BaseActivity<ActivityWebsocketBinding>() {
    override fun getViewBinding(): ActivityWebsocketBinding = ActivityWebsocketBinding.inflate(layoutInflater)

    private val viewModel: WebSocketViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.recordLog.observe(this, Observer { message ->
            binding.textLog.text = message
        })

        viewModel.isConnection.observe(this, Observer {
            window?.statusBarColor = when (it) {
                true -> R.color.pale_green
                false -> R.color.orange_red
            }.let { res ->
                resources.getColor(res, null)
            }
        })

        if (BuildConfig.DEBUG) {
            viewModel.connectLocal()
        }
    }
}