package com.jeff.startproject.enums

sealed class MyResult<out T : Any> {
    data class Success<out T : Any>(val data: T): MyResult<T>()

    sealed class Error(val exception: Exception): MyResult<Nothing>() {
        class FirstError(exception: Exception): Error(exception)
        class SecondError(exception: Exception): Error(exception)
    }

    object IntProgress: MyResult<Nothing>()
}