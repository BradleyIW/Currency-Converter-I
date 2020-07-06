package com.bradley.wilson.core.extensions.primitives

import android.text.format.DateFormat
import java.util.*

fun Long.toDateString(): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    return DateFormat.format("dd-MM-yyyy:HH:ss", calendar).toString()
}