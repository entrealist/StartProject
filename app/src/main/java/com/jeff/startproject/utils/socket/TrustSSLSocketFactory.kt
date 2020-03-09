package com.jeff.startproject.utils.socket

import java.net.InetAddress
import java.net.Socket
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class TrustSSLSocketFactory : SSLSocketFactory() {

    private val sslContext by lazy {
        SSLContext.getInstance("TLS")
    }

    init {
        val trustAllCerts: TrustManager = object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}
            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}
            override fun getAcceptedIssuers(): Array<X509Certificate> {
                TODO("Not yet implemented")
            }
        }

        sslContext.init(null, arrayOf(trustAllCerts), null)
    }

    override fun getDefaultCipherSuites(): Array<String> {
        TODO("Not yet implemented")
    }

    override fun getSupportedCipherSuites(): Array<String> {
        TODO("Not yet implemented")
    }

    override fun createSocket(): Socket {
        return sslContext.socketFactory.createSocket()
    }

    override fun createSocket(p0: String?, p1: Int): Socket {
        return sslContext.socketFactory.createSocket(p0, p1)
    }

    override fun createSocket(p0: InetAddress?, p1: Int): Socket {
        return sslContext.socketFactory.createSocket(p0, p1)
    }

    override fun createSocket(p0: String?, p1: Int, p2: InetAddress?, p3: Int): Socket {
        return sslContext.socketFactory.createSocket(p0, p1, p2, p3)
    }

    override fun createSocket(p0: Socket?, p1: String?, p2: Int, p3: Boolean): Socket {
        return sslContext.socketFactory.createSocket(p0, p1, p2, p3)
    }

    override fun createSocket(p0: InetAddress?, p1: Int, p2: InetAddress?, p3: Int): Socket {
        return sslContext.socketFactory.createSocket(p0, p1, p2, p3)
    }
}