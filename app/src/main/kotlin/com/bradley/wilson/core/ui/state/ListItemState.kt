package com.bradley.wilson.core.ui.state

sealed class ListItemState
object ItemClicked : ListItemState()
object ItemDormant : ListItemState()
