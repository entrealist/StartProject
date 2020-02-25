package com.jeff.startproject.model.api

import android.text.TextUtils
import com.jeff.startproject.BuildConfig
import com.jeff.startproject.model.api.ApiRepository.Companion.AUTHORIZATION
import com.jeff.startproject.model.api.ApiRepository.Companion.X_APP_VERSION
import com.jeff.startproject.model.pref.Pref
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.net.HttpURLConnection

class AuthInterceptor(private val pref: Pref) : Interceptor, KoinComponent {

    private val apiRepository: ApiRepository by inject()

    override fun intercept(chain: Interceptor.Chain): Response {

        val response = chain.proceed(chain.buildRequest())

        return when (response.code) {
            HttpURLConnection.HTTP_UNAUTHORIZED -> {
                //TODO: do refresh token, then new token save to pref.token
                /*
                CoroutineScope(Dispatchers.IO).launch {
                    apiRepository.refreshToken()
                }
                */
                chain.proceed(chain.buildRequest())
            }
            else -> response
        }
    }

    private fun Interceptor.Chain.buildRequest(): Request {
        val requestBuilder = request().newBuilder()
            .addHeader(X_APP_VERSION, BuildConfig.VERSION_NAME)

        if (!TextUtils.isEmpty(pref.token.accessToken)) {
            val auth = StringBuilder(ApiRepository.BEARER)
                .append(pref.token.accessToken)
                .toString()
            requestBuilder.addHeader(AUTHORIZATION, auth)
        }
        return requestBuilder.build()
    }
}