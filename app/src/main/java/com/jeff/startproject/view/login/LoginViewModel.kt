package com.jeff.startproject.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.jeff.startproject.model.sample.LoginUser
import com.utils.lifecycle.EditTextLiveData
import com.utils.lifecycle.EditTextMutableLiveData
import com.view.base.BaseViewModel
import java.math.BigDecimal

class LoginViewModel : BaseViewModel() {

    private val _mailLiveData = EditTextMutableLiveData("jeff@mail.com")
    val editTextMailLiveData: EditTextLiveData = _mailLiveData

    private val _passwordLiveData = EditTextMutableLiveData("123456")
    val editTextPasswordLiveData: EditTextLiveData = _passwordLiveData

    private val _loginUser = MutableLiveData<LoginUser>()
    val loginUser: LiveData<LoginUser> = _loginUser

    fun updatedLoginUser() {
        _loginUser.value = LoginUser(_mailLiveData.value, _passwordLiveData.value)
    }

    fun resetInput() {
        _mailLiveData.value = ""
        _passwordLiveData.value = ""
    }

    // Transformations Map
    val mapLiveData = Transformations.map(_mailLiveData, String::toUpperCase)

    // Transformations Switch Map
    val switchMapLiveData = Transformations.switchMap(_passwordLiveData) { string ->
        MutableLiveData<BigDecimal>().also { liveData ->
            liveData.value = string.toBigDecimalOrNull() ?: BigDecimal(0)
        }
    }
}