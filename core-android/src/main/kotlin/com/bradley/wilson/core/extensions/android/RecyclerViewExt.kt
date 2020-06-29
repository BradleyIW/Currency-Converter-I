package com.bradley.wilson.core.extensions.android

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.scrollToTop() = (layoutManager as LinearLayoutManager).scrollToPositionWithOffset(0, 1)

