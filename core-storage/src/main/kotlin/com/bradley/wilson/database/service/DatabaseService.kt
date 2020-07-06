@file:Suppress("TooGenericExceptionCaught")

package com.bradley.wilson.database.service

import android.database.sqlite.SQLiteException
import com.bradley.wilson.core.functional.Either
import com.bradley.wilson.database.error.DatabaseError
import com.bradley.wilson.database.error.DatabaseFailure
import com.bradley.wilson.database.error.DatabaseStateError
import com.bradley.wilson.database.error.NoResultsError
import com.bradley.wilson.database.error.SQLError

abstract class DatabaseService {
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
