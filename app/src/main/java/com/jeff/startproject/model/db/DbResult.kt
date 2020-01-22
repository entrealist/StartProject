package com.jeff.startproject.model.db

sealed class DbResult<T> {
    companion object {
        fun <T> loading(): DbResult<T> {
            return Loading()
        }

        fun <T> loaded(): DbResult<T> {
            return Loaded()
        }

        fun <T> error(throwable: Throwable): DbResult<T> {
            return Error(throwable)
        }

        fun <T> success(result: T?): DbResult<T> {
            return when (result) {
                null -> Empty()
                else -> Success(result)
            }
        }
    }

    data class Success<T>(val result: T) : DbResult<T>()

    data class Error<T>(val throwable: Throwable) : DbResult<T>()

    class Empty<T> : DbResult<T>()

    class Loading<T> : DbResult<T>()

    class Loaded<T> : DbResult<T>()
}