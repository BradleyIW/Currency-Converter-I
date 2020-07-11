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

package com.bradley.wilson.core.extensions.functional

import com.bradley.wilson.core.functional.Either

/**
 * Applies fnL if this is a Left or fnR if this is a Right.
 * @see Left
 * @see Right
 */
suspend fun <L, R, T> Either<L, R>.foldSuspendable(
    fnL: suspend (L) -> T?,
    fnR: suspend (R) -> T?
): T? =
    when (this) {
        is Either.Left -> fnL(a)
        is Either.Right -> fnR(b)
    }
