/**
 * Wire Copyright (C) 2020 Wire Swiss GmbH
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.bradley.wilson.core.functional

import com.bradley.wilson.core.exceptions.Failure
import com.bradley.wilson.core.extensions.functional.foldSuspendable

/**
 * A helper class that provides fallback actions to suspend functions that return [Either], in a functional way.
 *
 * When [primaryAction] returns [Either.Left], it invokes [fallbackAction] before propagating the [Failure] to upstream.
 * If [fallbackAction] is of type [Either.Right], it propagates this value as if the operation was successful.
 *
 * If a [fallbackSuccessAction] is provided, it also invokes the action upon successful [fallbackAction].
 */
data class FallbackOnFailure<R>(
    private val primaryAction: suspend () -> Either<Failure, R>,
    private val fallbackAction: suspend () -> Either<Failure, R>,
    private var fallbackSuccessAction: (suspend (R) -> Any)? = null
) {

    /**
     * Adds an optional [action] to be performed upon a successful [fallbackAction]. If [primaryAction] is
     * successful, and [fallbackAction] is never called, this [action] won't be called too.
     */
    fun finally(action: suspend (R) -> Any): FallbackOnFailure<R> = apply {
        fallbackSuccessAction = action
    }

    /**
     * Invokes given actions with the order of importance, if necessary.
     */
    suspend fun execute(): Either<Failure, R> =
        primaryAction().foldSuspendable({
            fallbackAction.invoke()
        }) {
            fallbackSuccessAction?.invoke(it)
            Either.Right(it)
        }!!
}

/**
 * Creates an [FallbackOnFailure] from the given suspend function, with its [FallbackOnFailure.primaryAction]
 * being the function itself, and [FallbackOnFailure.fallbackAction] as the given parameter.
 */
fun <R> (suspend () -> Either<Failure, R>).fallback(
    fallback: suspend () -> Either<Failure, R>
): FallbackOnFailure<R> = FallbackOnFailure(this, fallback)
