package com.jeff.startproject.model.db

sealed class DbResult<out T> {
    companion object {
        fun <T> success(result: T?): DbResult<T> {
            return when (result) {
                null -> SuccessNoContent
                else -> Success(result)
            }
        }

        fun <T> failure(throwable: Throwable): DbResult<T> {
            return Failure(throwable)
        }

        fun <T> loading(): DbResult<T> {
            return Loading
        }

        fun <T> loaded(): DbResult<T> {
            return Loaded
        }
    }

    data class Success<out T>(val data: T) : DbResult<T>()
    object SuccessNoContent : DbResult<Nothing>()
    data class Failure(val throwable: Throwable) : DbResult<Nothing>()
    object Loading : DbResult<Nothing>()
    object Loaded : DbResult<Nothing>()
}