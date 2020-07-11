package com.bradley.wilson.core.database.error

import com.bradley.wilson.core.exceptions.FeatureFailure

sealed class DatabaseFailure : FeatureFailure()

object DatabaseStateError : DatabaseFailure()
object SQLError : DatabaseFailure()
object DatabaseError : DatabaseFailure()
object NoResultsError : DatabaseFailure()
