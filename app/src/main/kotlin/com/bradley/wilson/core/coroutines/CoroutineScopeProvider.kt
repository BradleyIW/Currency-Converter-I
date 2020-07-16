package com.bradley.wilson.core.coroutines

import kotlinx.coroutines.CoroutineScope

open class CoroutineScopeProvider {
    open val scope: CoroutineScope? by lazy { null }
}