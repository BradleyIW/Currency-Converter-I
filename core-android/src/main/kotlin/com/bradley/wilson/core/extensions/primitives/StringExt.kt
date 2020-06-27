package com.bradley.wilson.core.extensions.primitives

import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.NumberFormat

fun String.Companion.empty() = ""

fun String.toCurrencyRate(): Double {
    val format = NumberFormat.getNumberInstance()
    if (format is DecimalFormat) {
        format.isParseBigDecimal = true
    }
    return (format.parse(this.replace(regex = "[^\\d.,]".toRegex(), replacement = "")) as BigDecimal).toDouble()
}
