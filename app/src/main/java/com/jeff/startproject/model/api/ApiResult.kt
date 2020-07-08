package com.jeff.startproject.model.api

sealed class ApiResult<out T> {
    companion object {
        fun <T> success(result: T?): ApiResult<T> {
            return when (result) {
                null -> SuccessNoContent
                else -> Success(result)
            }
        }

        fun <T> failure(throwable: Throwable): ApiResult<T> {
            return Failure(throwable)
        }

        fun <T> loading(): ApiResult<T> {
            return Loading
        }

        fun <T> loaded(): ApiResult<T> {
            return Loaded
        }
    }

    data class Success<out T>(val data: T) : ApiResult<T>()
    object SuccessNoContent : ApiResult<Nothing>()
    data class Failure(val throwable: Throwable) : ApiResult<Nothing>()
    object Loading : ApiResult<Nothing>()
    object Loaded : ApiResult<Nothing>()
}