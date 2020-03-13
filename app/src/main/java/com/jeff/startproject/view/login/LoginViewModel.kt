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

    private val mMailLiveData = EditTextMutableLiveData("jeff@mail.com")
    val editTextMailLiveData: EditTextLiveData = mMailLiveData

    private val mPasswordLiveData = EditTextMutableLiveData("123456")
    val editTextPasswordLiveData: EditTextLiveData = mPasswordLiveData

    private val mLoginUser = MutableLiveData<LoginUser>()
    val loginUser: LiveData<LoginUser> = mLoginUser

    fun updatedLoginUser() {
        mLoginUser.value = LoginUser(mMailLiveData.value, mPasswordLiveData.value)
    }

    fun resetInput() {
        mMailLiveData.value = ""
        mPasswordLiveData.value = ""
    }

    // Transformations Map
    val mapLiveData = Transformations.map(mMailLiveData, String::toUpperCase)

    // Transformations Switch Map
    val switchMapLiveData = Transformations.switchMap(mPasswordLiveData) { string ->
        MutableLiveData<BigDecimal>().also { liveData ->
            liveData.value = string.toBigDecimalOrNull() ?: BigDecimal(0)
        }
    }
}