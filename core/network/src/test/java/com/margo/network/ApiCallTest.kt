package com.margo.network

import com.margo.domain.common.ErrorType
import com.margo.domain.common.Result
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Response
import java.io.IOException

class ApiCallTest {

    @Test
    fun `safeApiCall returns Success when response is successful and body is not null`() = runTest {
        val expectedData = "data"
        val response = Response.success(expectedData)

        val result = safeApiCall { response }

        assertTrue(result is Result.Success)
        assertEquals(expectedData, (result as Result.Success).data)
    }

    @Test
    fun `safeApiCall returns Error UNKNOWN when response is successful but body is null`() = runTest {
        val response = Response.success<String>(null)

        val result = safeApiCall { response }

        assertTrue(result is Result.Error)
        assertEquals(ErrorType.UNKNOWN, (result as Result.Error).errorType)
        assertEquals("Body is null", result.exception?.message)
    }

    @Test
    fun `safeApiCall returns Error NOT_FOUND when response code is 404`() = runTest {
        val errorResponse = Response.error<String>(
            404,
            "Not Found".toResponseBody("application/json".toMediaTypeOrNull())
        )

        val result = safeApiCall { errorResponse }

        assertTrue(result is Result.Error)
        assertEquals(ErrorType.NOT_FOUND, (result as Result.Error).errorType)
    }

    @Test
    fun `safeApiCall returns Error SERVER_ERROR when response code is 500`() = runTest {
        val errorResponse = Response.error<String>(
            500,
            "Internal Server Error".toResponseBody("application/json".toMediaTypeOrNull())
        )

        val result = safeApiCall { errorResponse }

        assertTrue(result is Result.Error)
        assertEquals(ErrorType.SERVER_ERROR, (result as Result.Error).errorType)
    }

    @Test
    fun `safeApiCall returns Error UNKNOWN when response code is unhandled`() = runTest {
        val errorResponse = Response.error<String>(
            400,
            "Bad Request".toResponseBody("application/json".toMediaTypeOrNull())
        )

        val result = safeApiCall { errorResponse }

        assertTrue(result is Result.Error)
        assertEquals(ErrorType.UNKNOWN, (result as Result.Error).errorType)
    }

    @Test
    fun `safeApiCall returns Error NO_INTERNET when IOException is thrown`() = runTest {
        val apiCall: suspend () -> Response<String> = { throw IOException("No internet") }

        val result = safeApiCall { apiCall() }

        assertTrue(result is Result.Error)
        assertEquals(ErrorType.NO_INTERNET, (result as Result.Error).errorType)
    }

    @Test
    fun `safeApiCall returns Error UNKNOWN when Exception is thrown`() = runTest {
        val apiCall: suspend () -> Response<String> = { throw Exception("Some random error") }

        val result = safeApiCall { apiCall() }

        assertTrue(result is Result.Error)
        assertEquals(ErrorType.UNKNOWN, (result as Result.Error).errorType)
    }
}
