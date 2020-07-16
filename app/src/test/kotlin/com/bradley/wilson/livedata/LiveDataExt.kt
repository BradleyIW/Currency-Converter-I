package com.bradley.wilson.livedata
/*
 * Wire
 * Copyright (C) 2020 Wire Swiss GmbH
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * A function that suspends the current coroutine until the LiveData's value changes. Then it
 * resumes the coroutine with the new value.
 *
 * @throws TimeoutException if the value of LiveData is not updated within [timeout] seconds.
 */
suspend fun <T> LiveData<T>.awaitValue(timeout: Long = 2L): T = suspendCoroutine { cont ->
    val latch = CountDownLatch(1)

    this.observeOnce {
        latch.countDown()
        cont.resume(it)
    }

    if (!latch.await(timeout, TimeUnit.SECONDS)) {
        cont.resumeWithException(TimeoutException("Didn't receive LiveData value after $timeout seconds"))
    }
}

private fun <T> LiveData<T>.observeOnce(onChanged: (T) -> Unit) {
    val lifecycleOwner = TestLifecycleOwner()

    observe(lifecycleOwner, Observer {
        onChanged(it)
        lifecycleOwner.destroy()
    })
}
