@file:Suppress("UNCHECKED_CAST")

package com.bradley.wilson.network.api

import com.bradley.wilson.core.UnitTest
import com.bradley.wilson.core.functional.onFailure
import com.bradley.wilson.core.functional.onSuccess
import com.bradley.wilson.network.error.*
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import retrofit2.Response

class ApiServiceTest : UnitTest() {

    private lateinit var apiService: ApiService

    @Before
    fun setup() {
        apiService = object : ApiService() {}
    }

    @Test
    fun `given HTTP Request is invoked, when response body is not null, then propagate response body`() =
        runBlocking {
            val request = apiService.request { mockSuccessfulResponse() }
            request.onSuccess {
                assertEquals(it, Unit)
            }
            assertTrue(request.isRight)
        }

    @Test
    fun `given HTTP Request is invoked, when function is suspended, then propagate Cancelled error`() =
        runBlocking {
            validateThrowableError(CancellationException("Co-routine cancelled"), Cancelled)
        }

    @Test
    fun `given HTTP Request is invoked, when response code is 200 and response body is null, then propagate EmptyResponseBody error`() =
        runBlocking {
            validateSuccessError(EmptyResponseBody)
        }

    @Test
    fun `given HTTP Request is invoked, when response code is 400, then propagate BadRequest error`() =
        runBlocking {
            validateHttpError(CODE_BAD_REQUEST, BadRequest)
        }

    @Test
    fun `given HTTP Request is invoked, when response code is 401, then propagate Unauthorized error`() =
        runBlocking {
            validateHttpError(CODE_UNAUTHORIZED, Unauthorized)
        }

    @Test
    fun `given HTTP Request is invoked, when response code is 403, then propagate Forbidden error`() =
        runBlocking {
            validateHttpError(CODE_FORBIDDEN, Forbidden)
        }

    @Test
    fun `given HTTP Request is invoked, when response code is 404, then propagate NotFound error`() =
        runBlocking {
            validateHttpError(CODE_NOT_FOUND, NotFound)
        }

    @Test
    fun `given HTTP Request is invoked, when response code is 409, then propagate Conflict error`() =
        runBlocking {
            validateHttpError(CODE_CONFLICT, Conflict)
        }

    @Test
    fun `given HTTP Request is invoked, when response code is 500, then propagate InternalServerError error`() =
        runBlocking {
            validateHttpError(CODE_INTERNAL_SERVER_ERROR, InternalServerError)
        }

    @Test
    fun `given HTTP Request is invoked, when response code is 411, then propagate ServerError error`() =
        runBlocking {
            validateHttpError(411, ServerError)
        }

    private fun validateSuccessError(failure: NetworkFailure) = runBlocking {
        val request = apiService.request { mockEmptySuccessResponse() }
        request.onFailure {
            assertEquals(it, failure)
        }
        assertTrue(request.isLeft)
    }

    private fun validateThrowableError(exception: Exception, failure: NetworkFailure) =
        runBlocking {
            val request = apiService.request { mockThrowableResponse(exception) }
            request.onFailure {
                assertEquals(it, failure)
            }
            assertTrue(request.isLeft)
        }

    private fun validateHttpError(responseCode: Int, failure: NetworkFailure) = runBlocking {
        val request = apiService.request { mockErrorResponse(responseCode) }
        request.onFailure {
            assertEquals(it, failure)
        }
        assertTrue(request.isLeft)
    }

    private fun mockSuccessfulResponse(): Response<Unit> {
        val response = mock(Response::class.java)
        `when`(response.isSuccessful).thenReturn(true)
        `when`(response.body()).thenReturn(Unit)
        return response as Response<Unit>
    }

    private fun mockThrowableResponse(exception: Exception): Response<Any> {
        val response = mock(Response::class.java)
        `when`(response.isSuccessful).thenThrow(exception)
        return response as Response<Any>
    }

    private fun mockEmptySuccessResponse(): Response<Any> {
        val response = mock(Response::class.java)
        `when`(response.body()).thenReturn(null)
        `when`(response.isSuccessful).thenReturn(true)
        return response as Response<Any>
    }

    private fun mockErrorResponse(responseCode: Int): Response<Any> {
        val response = mock(Response::class.java)
        `when`(response.code()).thenReturn(responseCode)
        `when`(response.isSuccessful).thenReturn(false)
        return response as Response<Any>
    }

    companion object {
        private const val CODE_BAD_REQUEST = 400
        private const val CODE_UNAUTHORIZED = 401
        private const val CODE_FORBIDDEN = 403
        private const val CODE_NOT_FOUND = 404
        private const val CODE_CONFLICT = 409
        private const val CODE_INTERNAL_SERVER_ERROR = 500
    }
}