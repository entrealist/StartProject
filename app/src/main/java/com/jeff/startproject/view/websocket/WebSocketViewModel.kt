package com.jeff.startproject.view.websocket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jeff.startproject.BuildConfig
import com.jeff.startproject.utils.socket.JFWebSocketClient
import com.jeff.startproject.utils.socket.JFWebSocketListener
import com.jeff.startproject.view.base.LogcatViewModel
import com.log.JFLog
import org.java_websocket.handshake.ServerHandshake
import java.net.URI

class WebSocketViewModel : LogcatViewModel() {

    private val _isConnection = MutableLiveData(false)
    val isConnection: LiveData<Boolean> = _isConnection

    fun connectLocal() {
        val listener = object : JFWebSocketListener {
            override fun onOpen(handshake: ServerHandshake) {
                _isConnection.postValue(true)

                JFLog.d("onOpen status : ${handshake.httpStatus}").also {
                    appendMessage(it, isPost = true)
                }
                JFLog.d("onOpen message: ${handshake.httpStatusMessage}").also {
                    appendMessage(it, isPost = true)
                }
            }

            override fun onMessage(message: String) {
                JFLog.d("onMessage: $message").also {
                    appendMessage(it, isPost = true)
                }
            }

            override fun onClose(code: Int, reason: String?, remote: Boolean) {
                _isConnection.postValue(false)

                JFLog.d("onClose code: $code").also {
                    appendMessage(it, isPost = true)
                }
                JFLog.d("onClose reason: $reason").also {
                    appendMessage(it, isPost = true)
                }
                JFLog.d("onClose remote: $remote").also {
                    appendMessage(it, isPost = true)
                }
            }

            override fun onError(e: Exception) {
                _isConnection.postValue(false)

                JFLog.e(e).also {
                    appendMessage(it, isPost = true)
                }
            }
        }

        val uri = URI.create("ws://${BuildConfig.LOCAL_IP}:8080/ws/client")
        val socketClient = JFWebSocketClient(uri)
        socketClient.listener = listener
        socketClient.connect()
    }
}