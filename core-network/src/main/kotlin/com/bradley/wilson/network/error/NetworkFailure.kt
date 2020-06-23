package com.bradley.wilson.network.error

import com.bradley.wilson.core.exceptions.FeatureFailure

sealed class NetworkFailure : FeatureFailure()

object ServerError : NetworkFailure()
object BadRequest : NetworkFailure()
object NoConnection : NetworkFailure()
object Unauthorized : NetworkFailure()
object Forbidden : NetworkFailure()
object NotFound : NetworkFailure()
object Cancelled : NetworkFailure()
object InternalServerError : NetworkFailure()
object Conflict : NetworkFailure()
object EmptyResponseBody : NetworkFailure()

