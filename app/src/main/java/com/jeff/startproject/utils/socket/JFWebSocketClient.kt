package com.jeff.startproject.utils.socket

import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.lang.Exception
import java.net.URI
import javax.net.SocketFactory

interface JFWebSocketListener {
    fun onOpen(handshake: ServerHandshake)

    fun onMessage(message: String)

    fun onClose(code: Int, reason: String?, remote: Boolean)

    fun onError(e: Exception)
}

/**
 * TLS: Not working?
 */
class JFWebSocketClient(serverUriL: URI, isTLS: Boolean = false) :
    WebSocketClient(serverUriL) {

    var listener: JFWebSocketListener? = null

    init {
        val factory: SocketFactory =
            if (isTLS) {
                TrustSSLSocketFactory()
            } else {
                SocketFactory.getDefault()
            }

        factory.createSocket().also {
            it.keepAlive = true
            it.soTimeout = 60 * 1000
        }

        setSocketFactory(factory)
    }

    override fun onOpen(handshakedata: ServerHandshake?) {
        handshakedata?.also {
            listener?.onOpen(handshakedata)
        }
    }

    override fun onMessage(message: String?) {
        message?.also {
            listener?.onMessage(message)
        }
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        listener?.onClose(code, reason, remote)
    }

    override fun onError(e: Exception?) {
        e?.also {
            listener?.onError(e)
        }
    }
}