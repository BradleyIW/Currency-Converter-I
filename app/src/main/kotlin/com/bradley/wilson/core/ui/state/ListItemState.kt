package com.bradley.wilson.core.ui.state

sealed class ItemState
object ItemClicked : ItemState()
object ItemDormant : ItemState()
