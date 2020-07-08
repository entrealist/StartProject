package com.jeff.startproject.enums

sealed class ModelResult<out T> {
    companion object {
        fun <T> success(result: T?): ModelResult<T> {
            return when (result) {
                null -> SuccessNoContent
                else -> Success(result)
            }
        }

        fun <T> failure(throwable: Throwable): ModelResult<T> {
            return Failure(throwable)
        }

        fun <T> loading(): ModelResult<T> {
            return Loading
        }

        fun <T> loaded(): ModelResult<T> {
            return Loaded
        }
    }

    data class Success<out T>(val data: T) : ModelResult<T>()
    object SuccessNoContent : ModelResult<Nothing>()
    data class Failure(val throwable: Throwable) : ModelResult<Nothing>()
    object Loading : ModelResult<Nothing>()
    object Loaded : ModelResult<Nothing>()
}