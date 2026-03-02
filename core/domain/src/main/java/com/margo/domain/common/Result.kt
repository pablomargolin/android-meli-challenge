package com.margo.domain.common

/**
 * A discriminated union that encapsulates a successful outcome with a value of type [T]
 * or a failure with an [ErrorType] and an optional [Throwable].
 */
sealed interface Result<out T> {
    /**
     * Represents a successful outcome containing the requested [data].
     */
    data class Success<T>(val data: T) : Result<T>

    /**
     * Represents a failed outcome containing an [errorType] and an optional [exception].
     */
    data class Error(
        val errorType: ErrorType,
        val exception: Throwable? = null
    ) : Result<Nothing>
}

/**
 * Enumeration of predefined error types that can occur across the application boundaries.
 */
enum class ErrorType {
    /** Indicates a failure due to lack of network connectivity. */
    NO_INTERNET,
    /** Indicates a failure on the server side (HTTP 5xx). */
    SERVER_ERROR,
    /** Indicates that the requested resource was not found (HTTP 404). */
    NOT_FOUND,
    /** Indicates an unexpected or unhandled exception. */
    UNKNOWN
}