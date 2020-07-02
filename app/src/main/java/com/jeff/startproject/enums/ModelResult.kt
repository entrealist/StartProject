package com.jeff.startproject.enums

sealed class ModelResult<out T : Any> {
    data class Success<out T : Any>(val data: T?) : ModelResult<T>()
    data class Failure<out T : Any>(val data: T) : ModelResult<T>()
    sealed class Error(val exception: Exception) : ModelResult<Nothing>() {
        class GeneralError(exception: Exception) : Error(exception)
        class RuntimeError(exception: RuntimeException) : Error(exception)
    }

    object Progressing : ModelResult<Nothing>()
}