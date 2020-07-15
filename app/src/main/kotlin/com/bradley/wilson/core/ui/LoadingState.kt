package com.bradley.wilson.core.ui

sealed class LoadingState
object Loading : LoadingState()
object Loaded : LoadingState()
