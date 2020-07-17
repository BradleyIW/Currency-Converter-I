package com.bradley.wilson.core.ui

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

open class CoroutineDispatcherProvider {
    open val default: CoroutineDispatcher by lazy { Dispatchers.Default }
    open val main: CoroutineDispatcher by lazy { Dispatchers.Main }
    open val io: CoroutineDispatcher by lazy { Dispatchers.IO }
}
