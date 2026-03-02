package com.margo.network

import com.margo.domain.common.ErrorType
import retrofit2.Response
import com.margo.domain.common.Result
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Result<T> {
    return try {
        val response = apiCall()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                Result.Success(body)
            } else {
                Result.Error(ErrorType.UNKNOWN, Exception("Body is null"))
            }
        } else {
            val errorType = when (response.code()) {
                404 -> ErrorType.NOT_FOUND
                in 500..599 -> ErrorType.SERVER_ERROR
                else -> ErrorType.UNKNOWN
            }
            Result.Error(errorType, HttpException(response))
        }
    } catch (e: IOException) {
        Timber.w(e, "Network or timeout error in safeApiCall")
        Result.Error(ErrorType.NO_INTERNET, e)
    } catch (e: Exception) {
        Timber.e(e, "Unexpected exception in safeApiCall")
        Result.Error(ErrorType.UNKNOWN, e)
    }
}