package com.margo.domain.common

sealed interface Result<out T> {
    data class Success<T>(val data: T) : Result<T>
    data class Error(
        val errorType: ErrorType,
        val exception: Throwable? = null
    ) : Result<Nothing>
}
enum class ErrorType {
    NO_INTERNET,
    SERVER_ERROR,
    NOT_FOUND,
    UNKNOWN
}