package com.bradley.wilson.core.ui.state

sealed class LoadingState
object Loading : LoadingState()
object Loaded : LoadingState()
