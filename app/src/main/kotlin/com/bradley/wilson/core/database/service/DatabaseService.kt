@file:Suppress("TooGenericExceptionCaught")

package com.bradley.wilson.core.database.service

import android.database.sqlite.SQLiteException
import com.bradley.wilson.core.database.error.DatabaseError
import com.bradley.wilson.core.database.error.DatabaseFailure
import com.bradley.wilson.core.database.error.DatabaseStateError
import com.bradley.wilson.core.database.error.NoResultsError
import com.bradley.wilson.core.database.error.SQLError
import com.bradley.wilson.core.functional.Either

open class DatabaseService {
    suspend fun <R> request(localRequest: suspend () -> R): Either<DatabaseFailure, R> =
        try {
            localRequest()?.let {
                Either.Right(it)
            } ?: Either.Left(NoResultsError)
        } catch (e: IllegalStateException) {
            Either.Left(DatabaseStateError)
        } catch (e: SQLiteException) {
            Either.Left(SQLError)
        } catch (e: Exception) {
            Either.Left(DatabaseError)
        }
}

