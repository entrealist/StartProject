package com.jeff.startproject.view.websocket

import android.os.Bundle
import androidx.lifecycle.Observer
import com.jeff.startproject.BuildConfig
import com.jeff.startproject.databinding.ActivityWebsocketBinding
import com.jeff.startproject.utils.socket.JFWebSocketClient
import com.jeff.startproject.utils.socket.JFWebSocketListener
import com.jeff.startproject.view.base.BaseActivity
import com.log.JFLog
import org.java_websocket.handshake.ServerHandshake
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.Exception
import java.net.URI

class WebSocketActivity : BaseActivity<ActivityWebsocketBinding>() {
    override fun getViewBinding(): ActivityWebsocketBinding =
        ActivityWebsocketBinding.inflate(layoutInflater)

    private val viewModel by viewModel<WebSocketViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.recordLog.observe(this, Observer { message ->
            binding.textLog.text = message
        })

        if (BuildConfig.DEBUG) {
            val listener = object : JFWebSocketListener {
                override fun onOpen(handshake: ServerHandshake) {
                    JFLog.d("onOpen status : ${handshake.httpStatus}").also {
                        viewModel.appendMessage(it, isPost = true)
                    }
                    JFLog.d("onOpen message: ${handshake.httpStatusMessage}").also {
                        viewModel.appendMessage(it, isPost = true)
                    }
                }

                override fun onMessage(message: String) {
                    JFLog.d("onMessage: $message").also {
                        viewModel.appendMessage(it, isPost = true)
                    }
                }

                override fun onClose(code: Int, reason: String?, remote: Boolean) {
                    JFLog.d("onClose code: $code").also {
                        viewModel.appendMessage(it, isPost = true)
                    }
                    JFLog.d("onClose reason: $reason").also {
                        viewModel.appendMessage(it, isPost = true)
                    }
                    JFLog.d("onClose remote: $remote").also {
                        viewModel.appendMessage(it, isPost = true)
                    }
                }

                override fun onError(e: Exception) {
                    JFLog.e(e).also {
                        viewModel.appendMessage(it, isPost = true)
                    }
                }
            }

            val uri = URI.create("ws://${BuildConfig.LOCAL_IP}:8080/ws/client")
            val socketClient = JFWebSocketClient(uri)
            socketClient.listener = listener
            socketClient.connect()
        }
    }
}