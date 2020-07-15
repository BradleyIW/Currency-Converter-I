package com.bradley.wilson.core.ui

sealed class ItemState
object ItemClicked : ItemState()
object ItemDormant : ItemState()
