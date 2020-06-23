package com.bradley.wilson.database.error

import com.bradley.wilson.core.exceptions.FeatureFailure

sealed class DatabaseFailure : FeatureFailure()

object DatabaseStateError : DatabaseFailure()
object SQLError : DatabaseFailure()
object DatabaseError : DatabaseFailure()
