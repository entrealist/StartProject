package com.jeff.startproject.model.sample

import android.util.Patterns

data class LoginUser(val mail: String?, val password: String?) {

    val isMailValid: Boolean
        get() = Patterns.EMAIL_ADDRESS.matcher(mail).matches()

    val isPasswordValid: Boolean
        get() {
            if (password != null) {
                return password.length > 5
            }
            return false
        }
}