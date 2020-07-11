@file:Suppress("TooGenericExceptionCaught", "UnnecessaryAbstractClass")

package com.bradley.wilson.core.network.api

import com.bradley.wilson.core.exceptions.Failure
import com.bradley.wilson.core.functional.Either
import com.bradley.wilson.network.error.BadRequest
import com.bradley.wilson.network.error.Cancelled
import com.bradley.wilson.network.error.Conflict
import com.bradley.wilson.network.error.EmptyResponseBody
import com.bradley.wilson.network.error.Forbidden
import com.bradley.wilson.network.error.InternalServerError
import com.bradley.wilson.network.error.NoConnection
import com.bradley.wilson.network.error.NotFound
import com.bradley.wilson.network.error.ServerError
import com.bradley.wilson.network.error.Unauthorized
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.net.SocketTimeoutException

class ConnectionException : Exception()

abstract class ApiService {

    suspend fun <T> request(call: suspend () -> Response<T>): Either<Failure, T> =
        withContext(Dispatchers.IO) {
            performRequest(call)
        }

    private suspend fun <T> performRequest(
        call: suspend () -> Response<T>
    ): Either<Failure, T> {
        return try {
            val response = call()
            if (response.isSuccessful) {
                response.body()?.let { Either.Right(it) }
                    ?: Either.Left(EmptyResponseBody)
            } else {
                handleRequestError(response)
            }
        } catch (exception: Exception) {
            when (exception) {
                is ConnectionException -> Either.Left(NoConnection)
                is SocketTimeoutException -> Either.Left(NoConnection)
                is CancellationException -> Either.Left(Cancelled)
                else -> Either.Left(ServerError)
            }
        }
    }

    private fun <T> handleRequestError(response: Response<T>): Either<Failure, T> =
        when (response.code()) {
            CODE_BAD_REQUEST -> Either.Left(BadRequest)
            CODE_UNAUTHORIZED -> Either.Left(Unauthorized)
            CODE_FORBIDDEN -> Either.Left(Forbidden)
            CODE_NOT_FOUND -> Either.Left(NotFound)
            CODE_CONFLICT -> Either.Left(Conflict)
            CODE_INTERNAL_SERVER_ERROR -> Either.Left(InternalServerError)
            else -> Either.Left(ServerError)
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
