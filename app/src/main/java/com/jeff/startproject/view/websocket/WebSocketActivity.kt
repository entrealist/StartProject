package com.jeff.startproject.view.websocket

import android.os.Bundle
import com.jeff.startproject.databinding.ActivityWebsocketBinding
import com.jeff.startproject.view.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class WebSocketActivity : BaseActivity<ActivityWebsocketBinding>() {
    override fun getViewBinding(): ActivityWebsocketBinding = ActivityWebsocketBinding.inflate(layoutInflater)

    private val viewModel by viewModel<WebSocketViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}