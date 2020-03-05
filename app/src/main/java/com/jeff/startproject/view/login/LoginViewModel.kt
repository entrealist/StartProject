package com.jeff.startproject.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.jeff.startproject.model.sample.LoginUser
import com.jeff.startproject.utils.LiveDataTextWatcher
import com.jeff.startproject.view.base.BaseViewModel
import java.math.BigDecimal

class LoginViewModel : BaseViewModel() {

    private val mMail = MutableLiveData<String>().also { it.value = "jeff@mail.com" }
    val mail: LiveData<String> = mMail
    val mailTextWatcher = LiveDataTextWatcher(mMail)

    private val mPassword = MutableLiveData<String>().also { it.value = "123456" }
    val password: LiveData<String> = mPassword
    val passwordTextWatcher = LiveDataTextWatcher(mPassword)

    private val mLoginUser = MutableLiveData<LoginUser>()
    val loginUser: LiveData<LoginUser> = mLoginUser

    fun updatedLoginUser() {
        mLoginUser.value = LoginUser(mMail.value, mPassword.value)
    }

    fun resetInput() {
        mMail.value = ""
        mPassword.value = ""
    }

    // Transformations Map
    val liveDataMap = Transformations.map(mMail, String::toUpperCase)

    // Transformations Switch Map
    val liveDataSwitchMap = Transformations.switchMap(mPassword) { string ->
        MutableLiveData<BigDecimal>().also { liveData ->
            liveData.value = string.toBigDecimalOrNull() ?: BigDecimal(0)
        }
    }
}